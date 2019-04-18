package com.warne.disruptor;

import com.warne.disruptor.config.ApplicationConfig;
import com.warne.disruptor.service.IService;
import com.warne.disruptor.service.OrderModuleQueue;
import com.warne.disruptor.util.SnowFlake;
import com.warne.disruptor.util.Tools;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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


    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        OrderModuleQueue orderModuleQueue = ac.getBean(OrderModuleQueue.class);
        final String orderId = snowFlake.id();
        final String userId = snowFlake.id();

        System.out.println("\r\n==================> starting <==================\r\n");
        Thread.sleep(800);

        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");

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

                if ((currentTimeMillis() - start) >= perSeconds) {

                    log.info("===> 每 {} 秒处理 {} 条消息. ( 1. place order -> 2. send email )", seconds, COUNT);
                    COUNT.getAndSet(0);
                    break; //# 继续下一个时间间隔
                }
            }
        }
    }
}
