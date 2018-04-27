package com.jt.manage.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.vo.EasyUITree;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Order(1)
@Component
@Aspect
public class RedisAspect {

    @Autowired
    private JedisCluster jedisCluster;

    private ObjectMapper om = new ObjectMapper();

    @Pointcut("execution(* com.jt.manage.service.ItemCatServiceImpl.findEasyUIList(..))" +
            "&& args(parentId)")
    public void perform(Long parentId) {
    }

    Logger logger = Logger.getLogger("日志记录");

    @Around("perform(parentId)")
    public List<EasyUITree> findEasyUIList(ProceedingJoinPoint joinPoint, Long parentId) {
        /*1. 定义key值*/
        String key = joinPoint.getSignature().getName() + parentId;
        /*2. 从缓存中获取数据*/
        String jsonData = jedisCluster.get(key);
        /*3. 判断缓存中是否有数据*/
        List<EasyUITree> easyUITrees = null;
        try {
            if (!StringUtils.isEmpty(jsonData)) {
                /*4. 直接从缓存中取出并返回*/
                easyUITrees = Arrays.asList(om.readValue(jsonData, EasyUITree[].class));
                logger.info("从缓存中取出：" + jsonData);
                return easyUITrees;
            }
            /*5. 执行目标对象中方法从数据库中取得数据*/
            easyUITrees = (List<EasyUITree>) joinPoint.proceed();
            /*6. 将数据存入缓存中*/
            String itemCatJson = om.writeValueAsString(easyUITrees);
            jedisCluster.set(key, itemCatJson);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return easyUITrees;
    }

    /**
     * 这里使用json串存入缓存
     * 如果将对象序列化成二进制再存入缓存，代码将更简洁(pojo必须实现序列化接口)
     */
    @Around("execution(* com.jt.manage.service.ItemCatServiceImpl.findItemCatAll(..))")
    public ItemCatResult findItemCatAll(ProceedingJoinPoint joinPoint) {
        List<ItemCatData> itemCatDataList = null;
        ItemCatResult itemCatResult = null;

        // 从缓存中取出json
        String key = "findItemCatAll";
        String cacheResult = jedisCluster.get(key);
        try {
            // 如果是空的则从数据库中查询
            if (StringUtils.isEmpty(cacheResult)) {
                // 从数据库中取出数据
                itemCatResult = (ItemCatResult) joinPoint.proceed();
                // 存入缓存中
                itemCatDataList = itemCatResult.getItemCats();
                String jsonResult = om.writeValueAsString(itemCatDataList);
                jedisCluster.set(key, jsonResult);
            }else {
                // 从缓存中取出所有目录
                logger.info("--------------------从缓存中取出了所有目录-----------------------");
                ItemCatData[] itemCatDatas = om.readValue(cacheResult, ItemCatData[].class);
                itemCatDataList = Arrays.asList(itemCatDatas);
                // 封装成控制层需要的结果并返回
                itemCatResult = new ItemCatResult();
                itemCatResult.setItemCats(itemCatDataList);
                return itemCatResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}

