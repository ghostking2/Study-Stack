package com.warne.disruptor.service;

import com.lmax.disruptor.EventHandler;
import com.warne.disruptor.DisruptorDemo;
import com.warne.disruptor.bean.OrderInfoEvent;

/**
 * function：description
 * datetime：2019-04-16 14:09
 * author：warne
 */

public class EndHandler implements IService, EventHandler<OrderInfoEvent> {

    @Override
    public void onEvent(OrderInfoEvent event, long sequence, boolean endOfBatch) {
        if (event == null)
            throw new RuntimeException("event is empty ");

        DisruptorDemo.COUNT.getAndIncrement();
    }
}
