package com.yusys.agile.redis.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @description redisson业务接口类
 * @date 2020/04/26
 */
public interface RedissonService {

    /**
     * @param name
     * @return
     * @description 获取锁
     */
    RLock getLock(String name);

    /**
     * @param name
     * @return
     * @description 获取锁
     */
    RLock lock(String name);

    /**
     * @param name
     * @param leaseTime
     * @return
     * @description 获取锁
     */
    RLock lock(String name, long leaseTime);

    /**
     * @param name
     * @param leaseTime
     * @param unit
     * @return
     * @description 获取锁
     */
    RLock lock(String name, long leaseTime, TimeUnit unit);

    /**
     * @param name
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     * @description 获取锁
     */
    boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * @param name
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @param retryTimes
     * @return
     * @description 获取锁
     */
    boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit, int retryTimes);

    /**
     * @param name
     * @description 释放锁
     */
    void unlock(String name);

    /**
     * @param rLock
     * @description 释放锁
     */
    void unlock(RLock rLock);

}
