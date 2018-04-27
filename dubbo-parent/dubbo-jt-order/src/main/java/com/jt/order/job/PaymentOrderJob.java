package com.jt.order.job;

import com.jt.order.mapper.OrderMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Calendar;
import java.util.logging.Logger;

public class PaymentOrderJob extends QuartzJobBean {
    Logger logger = Logger.getLogger("定时器");

    /**
     * 1. 通过定时任务的上下文件获取spring容器
     * 2. 从spring容器中获取需要的对象
     * 3. 获取对象后，执行定时任务的具体任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 1. 获取spring容器
        ApplicationContext applicationContext =
                (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");

        // 2. 获取orderMapper对象
        OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);

        // 3. 实现数据的修改操作
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);

        orderMapper.updateStatus(calendar.getTime());
        logger.info("定时清理一次数据库订单");
    }
}
