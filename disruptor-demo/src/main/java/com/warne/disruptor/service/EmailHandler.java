/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           EmailHandler
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.service;

import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.warne.disruptor.DisruptorDemo;
import com.warne.disruptor.bean.OrderInfoEvent;
import com.warne.disruptor.util.EmailUtil;
import org.bson.Document;

import java.util.List;

/**
 * function：description
 * datetime：2019-04-16 14:09
 * author：warne
 */

public class EmailHandler implements IService, EventHandler<OrderInfoEvent> {

    public static List<Document> eamilInfoList = Lists.newLinkedList();

    @Override
    public void onEvent(OrderInfoEvent event, long sequence, boolean endOfBatch) {
        if (event == null)
            throw new RuntimeException("event info is empty ");

        eamilInfoList.add(event.getValue());

        if (eamilInfoList.size() >= 100000) {
            EmailUtil.sendEmail(eamilInfoList);
            eamilInfoList = Lists.newLinkedList();
        }

        DisruptorDemo.COUNT.getAndIncrement();
        DisruptorDemo.MESSAGE_SUM.getAndIncrement();
    }

    /**
     * todo 发送邮件
     *
     * @param to
     * @param content
     */
    private void sendEmail(String to, String content) {


    }
}
