/*
 *
 *  *   @copyright      Copyright© 2018. 贵阳天德信链科技有限公司 All rights reserved.
 *  *   @project        tdchain-boot-common
 *  *   @file           IdGen
 *  *   @author         warne
 *  *   @date           18-9-10 下午3:23
 *
 */

package com.warne.disruptor.util;

import java.util.UUID;

/**
 * @desc: snowflake 64位自增ID算法详解 form twitter
 * <p>
 * 雪花算法
 * @date: 2017/10/10 17:35
 * @author: warne
 */

public class SnowFlake {

    private final static long workerIdBits = 6L;

    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final static long sequenceBits = 6L;

    private final static long workerIdShift = sequenceBits;

    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId = Long.valueOf(Math.round(63));

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    private final static long twepoch = 1452866247339L;

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public SnowFlake() {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    public SnowFlake(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return (timestamp - twepoch << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * 重载一个 string id
     *
     * @return
     */
    public String id() {
        return String.valueOf(nextId());
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * get uuid with '-'
     * <p>
     * length = 36
     *
     * @return
     */
    public String uuidWithLine() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * length = 32
     *
     * @return
     */
    public String uuid() {
        String uuid = uuidWithLine();
        String str = uuid.replaceAll("-+", "");
        return str;
    }

   /* public static void main(String[] args) {
        LinkedList<Long> ids = Lists.newLinkedList();
        SnowFlake snowFlake = new SnowFlake();
        Long start = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - start >= 1003) {
                break;
            }
            long id = snowFlake.nextId();
            System.out.println(id);
            ids.addLast(id);
        }

        System.out.println("id size: " + ids.size());
        System.out.println("id size: " + Sets.newHashSet(ids).size());

    }*/
}
