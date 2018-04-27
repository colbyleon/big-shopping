package com.jt.web.intercept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;


public class UserInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger("用户拦截器");

    UserInterceptor(){
        logger.info("拦截器启动啦.......................................................................");
    }

    @Autowired
    private JedisCluster jedisCluster;

    private ObjectMapper objectMapper = new ObjectMapper();

    // 处理器执行之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("被我拦截了.............................................................................");
        // 通过拦截器实现用户信息的拦截
        // 1. 检测cookie中是否有值
        String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
        // 2. 检查redis中是否含有ticket
        if (!StringUtils.isEmpty(ticket)) {
            String userJSON = jedisCluster.get(ticket);
            if (!StringUtils.isEmpty(userJSON)) {
                User user = objectMapper.readValue(userJSON, User.class);
                UserThreadLocal.set(user);
                // 3. 如果用户已经存在刚将用户信息进行包装，方便购物车获取用户
                return true;
            }
        }
        // 4. 如果redis和cookie中没有ticket则转向用户登陆页面
        response.sendRedirect("/user/login.html");
        return false;
    }

    // 处理器执行之后
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 请求完成之后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 返回视图之前，将ThreadLocal移除
        /* 如果不擦除value的生命周期可能会和线程一样长
        *  这里是用的线程池，那么问题更严重，线程几乎不会被关闭
        *  而且可能获取到以前的存在线程的localThreadMap中数据被取出，造成业务逻辑混乱 */
        UserThreadLocal.remove();
    }
}
