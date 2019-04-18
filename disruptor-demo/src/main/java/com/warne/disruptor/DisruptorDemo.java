/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           DisruptorDemo
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor;

import com.warne.disruptor.config.ApplicationConfig;
import com.warne.disruptor.mongodb.MongoHelper;
import com.warne.disruptor.service.EmailHandler;
import com.warne.disruptor.service.IService;
import com.warne.disruptor.service.OrderInfoHandler;
import com.warne.disruptor.service.OrderModuleQueue;
import com.warne.disruptor.util.EmailUtil;
import com.warne.disruptor.util.SnowFlake;
import com.warne.disruptor.util.Tools;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.currentTimeMillis;

/**
 * function：description
 * datetime：2019-04-16 16:48
 * author：warne
 */
public class DisruptorDemo implements IService {

    static SnowFlake snowFlake = new SnowFlake();
    static int seconds = 1;
    static long perSeconds = seconds * 1000;
    public final static AtomicLong COUNT = new AtomicLong(1);
    public final static AtomicLong MESSAGE_SUM = new AtomicLong(1);

    public static int loop = 20;

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        OrderModuleQueue orderModuleQueue = ac.getBean(OrderModuleQueue.class);

        startTask(orderModuleQueue);
    }


    /**
     * testing
     *
     * @param orderModuleQueue
     * @throws InterruptedException
     */
    static void startTask(OrderModuleQueue orderModuleQueue) throws Exception {
        final String orderId = snowFlake.id();
        final String userId = snowFlake.id();

        System.out.println("\r\n==================> starting <==================\r\n");
        Thread.sleep(500);
        int index = 1;
        while (true) {
            long start = currentTimeMillis();
            boolean flag = true;
            Document orderInfo = null;
            for (; flag; ) {
                orderInfo = new Document();
                orderInfo.append("orderId", orderId)
                        .append("userId", userId)
                        .append("orderDate", Tools.now())
                        .append("amount", 100)
                        .append("count", 180).append("to", "hello@126.com")
                        .append("content", "hello");

                orderModuleQueue.publishEvent(orderInfo);

                long consumeTime = (currentTimeMillis() - start);
                if (consumeTime >= perSeconds) {
                    long sum = COUNT.get();
                    float perSeconds = BigDecimal.valueOf(consumeTime).divide(BigDecimal.valueOf(1000.00), 1, BigDecimal.ROUND_HALF_UP).floatValue();
                    float average = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(perSeconds), 1, BigDecimal.ROUND_HALF_UP).floatValue();
                    log.info("===>index={}, 耗时 {} 秒处理了 {} 条消息， 平均每秒 {} 条消息. ", index, perSeconds, sum, average);
                    COUNT.getAndSet(0);
                    if (index >= loop) {
                        Thread.sleep(5000);
                        log.info(" 总共处理了 {} 条消息. ", (MESSAGE_SUM.get() - 1)); //# ( - 1) 最后一一次也被添加了，但是没有被消费掉


                        //# 保证数据全部入库吗，全部消息要被消费完
                        if (OrderInfoHandler.orderInfoList.size() > 0) {
                            MongoHelper.save(OrderInfoHandler.orderInfoList);
                        }

                        if (EmailHandler.eamilInfoList.size() > 0) {
                            EmailUtil.sendEmail((EmailHandler.eamilInfoList));
                        }

                        System.exit(1);
                    }

                    index++;
                    break; //# 继续下一个时间间隔
                }

            }
        }
    }
}
