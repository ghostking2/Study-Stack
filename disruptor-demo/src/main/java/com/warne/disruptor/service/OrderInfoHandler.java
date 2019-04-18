package com.warne.disruptor.service;

import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.warne.disruptor.bean.OrderInfoEvent;
import com.warne.disruptor.mongodb.MongoTools;
import org.bson.Document;

import java.util.List;

/**
 * function：description
 * datetime：2019-04-16 14:09
 * author：warne
 */

public class OrderInfoHandler implements IService, EventHandler<OrderInfoEvent> {

    final static Integer BATCH_SIZE = 100_0000;

    List<Document> orderInfoList = Lists.newLinkedList();


    @Override
    public void onEvent(OrderInfoEvent event, long sequence, boolean endOfBatch) {
        if (event == null)
            throw new RuntimeException("event is empty ");

        //# todo 业务处理 保存订单
        Document orderInfo = event.getValue();
        if (orderInfo == null)
            throw new RuntimeException("order info is empty ");

        orderInfoList.add(orderInfo);

        //# 批量提交数据
        if (orderInfoList.size() >= BATCH_SIZE) {
            MongoTools.save(orderInfoList);
            orderInfoList = Lists.newLinkedList();
            return;
        }
    }

}
