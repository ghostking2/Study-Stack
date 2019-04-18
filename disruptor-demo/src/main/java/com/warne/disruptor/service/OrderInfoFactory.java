/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           OrderInfoFactory
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.service;

import com.lmax.disruptor.EventFactory;
import com.warne.disruptor.bean.OrderInfoEvent;

/**
 * function：description
 * datetime：2019-04-16 14:06
 * author：warne
 */
public class OrderInfoFactory implements EventFactory<OrderInfoEvent> {
    public OrderInfoEvent newInstance() {
        return new OrderInfoEvent();
    }
}
