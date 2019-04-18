/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           ValueWrapper
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.bean;

/**
 * function：description
 * datetime：2019-04-16 14:21
 * author：warne
 */

public class ValueWrapper<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
