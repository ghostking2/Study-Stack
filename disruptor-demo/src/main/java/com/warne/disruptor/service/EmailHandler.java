package com.warne.disruptor.service;

import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.warne.disruptor.bean.OrderInfoEvent;
import org.bson.Document;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * function：description
 * datetime：2019-04-16 14:09
 * author：warne
 */

public class EmailHandler implements IService, EventHandler<OrderInfoEvent> {

    ExecutorService executorService = Executors.newCachedThreadPool();
    List<Document> orderInfoList = Lists.newLinkedList();

    @Override
    public void onEvent(OrderInfoEvent event, long sequence, boolean endOfBatch) {
        if (event == null)
            throw new RuntimeException("event info is empty ");

        orderInfoList.add(event.getValue());

        if (orderInfoList.size() >= 100000) {
            for (Document info : orderInfoList) {
                String toMail = info.getString("to");
                String mailContent = info.getString("content");

                sendEmail(toMail, mailContent);
            }

            orderInfoList = Lists.newLinkedList();
        }
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
