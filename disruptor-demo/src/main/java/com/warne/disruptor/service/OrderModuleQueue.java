/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           OrderModuleQueue
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.service;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.warne.disruptor.bean.OrderInfoEvent;
import org.bson.Document;

/**
 * function：description
 * datetime：2019-04-16 14:15
 * author：warne
 */

public class OrderModuleQueue extends BaseQueue<Document, OrderInfoEvent> implements IService {

    protected EventFactory<OrderInfoEvent> eventFactory() {
        return new OrderInfoFactory();
    }


    @Override
    protected void handlersAndThenHandlers(final Disruptor disruptor) {
        if (disruptor == null) {
            log.error("disruptor is empty, it is not going to work !");
            throw new RuntimeException("disruptor is empty, it is not going to work !");
        }

        //# 先执行完order操作，然后在发送邮件通知，整体算一次操作,即为一个消息
        disruptor.handleEventsWith(new OrderInfoHandler()).then(new EmailHandler());

        //disruptor.handleEventsWith(new OrderInfoHandler());
    }
}
