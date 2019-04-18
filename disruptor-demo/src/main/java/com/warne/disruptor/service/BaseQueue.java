package com.warne.disruptor.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.warne.disruptor.bean.ValueWrapper;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;

/**
 * function：description
 * datetime：2019-04-16 14:20
 * author：warne
 */

public abstract class BaseQueue<Data, Event extends ValueWrapper<Data>> implements InitializingBean, IService {

    private List<BaseQueue> queueList = new CopyOnWriteArrayList<>();

    private Disruptor<Event> disruptor;

    private RingBuffer<Event> ringBuffer;

    private List<Data> dataCacheList = Lists.newArrayList();

    private int DEFAULT_RING_BUFFER_SIZE = 1024;

    protected abstract EventFactory<Event> eventFactory();

    protected abstract void handlersAndThenHandlers(Disruptor disruptor);

    private void init() {

        int ringBufferSize = ringBufferSize();
        //# 若果不是2的幂次方
        if (ringBufferSize < 0 || (ringBufferSize & (ringBufferSize - 1)) != 0) {
            log.warn("ring buffer size [{}] is not pow(2,n), so use default size is {}", ringBufferSize, DEFAULT_RING_BUFFER_SIZE);
            ringBufferSize = DEFAULT_RING_BUFFER_SIZE;
        }

        disruptor = new Disruptor<>(eventFactory(), ringBufferSize, threadFactory(), producerType(), strategy());

        this.handlersAndThenHandlers(disruptor);

        ringBuffer = disruptor.start();


        if (dataCacheList != null && dataCacheList.size() > 0) {
            for (final Data data : dataCacheList) {
                ringBuffer.publishEvent((event, sequence, arg) -> event.setValue(arg), data);

            }

            if (queueList.size() > 0) {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    for (BaseQueue service : queueList) {
                        service.close();
                    }
                }));
            }

        }
        queueList.add(this);
    }

    /**
     * 添加事件
     *
     * @param data
     * @return
     */
    public int publishEvent(Data data) {
        try {
            if (ringBuffer == null) {
                dataCacheList.add(data);
                return 0;
            }

            ringBuffer.publishEvent((event, sequence, arg) -> event.setValue(arg), data);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("add data error, data: {} . desc: {}", JSONObject.toJSONString(data), e);
        }

        return -1;
    }

    /**
     * 添加事件
     *
     * @param dataList
     * @return
     */
    public int publishEvent(List<Data> dataList) {

        if (dataList == null || dataList.size() == 0)
            return 3;

        for (Data data : dataList)
            publishEvent(data);

        return 1;
    }


    /**
     * BlockingWaitStrategy	加锁	CPU资源紧缺，吞吐量和延迟并不重要的场景
     * BusySpinWaitStrategy	自旋	通过不断重试，减少切换线程导致的系统调用，而降低延迟。推荐在线程绑定到固定的CPU的场景下使用
     * PhasedBackoffWaitStrategy	自旋 + yield + 自定义策略	CPU资源紧缺，吞吐量和延迟并不重要的场景
     * SleepingWaitStrategy	自旋 + yield + sleep	性能和CPU资源之间有很好的折中。延迟不均匀
     * TimeoutBlockingWaitStrategy	加锁，有超时限制	CPU资源紧缺，吞吐量和延迟并不重要的场景
     * YieldingWaitStrategy	自旋 + yield + 自旋	性能和CPU资源之间有很好的折中。延迟比较均匀
     *
     * @return
     */
    protected WaitStrategy strategy() {
        return new YieldingWaitStrategy();
    }

    protected int ringBufferSize() {
        return DEFAULT_RING_BUFFER_SIZE;
    }

    protected ProducerType producerType() {
        return ProducerType.SINGLE;
    }

    protected ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder().setNameFormat(BaseQueue.class.getSimpleName()).build();
    }

    protected void close() {
        if (disruptor != null)
            disruptor.shutdown();
    }

    public void afterPropertiesSet() {
        this.init();
    }
}
