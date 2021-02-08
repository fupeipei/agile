package com.yusys.agile.redis.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @description redisson业务接口类
 *  
 * @date 2020/04/26
 */
public interface RedissonService {

    /**
     * @description 获取锁
     * @param name
     * @return
     */
    RLock getLock(String name);

    /**
     * @description 获取锁
     * @param name
     * @return
     */
    RLock lock(String name);

    /**
     * @description 获取锁
     * @param name
     * @param leaseTime
     * @return
     */
    RLock lock(String name, long leaseTime);

    /**
     * @description 获取锁
     * @param name
     * @param leaseTime
     * @param unit
     * @return
     */
    RLock lock(String name, long leaseTime, TimeUnit unit);

    /**
     * @description 获取锁
     * @param name
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * @description 获取锁
     * @param name
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @param retryTimes
     * @return
     */
    boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit, int retryTimes);

    /**
     * @description 释放锁
     * @param name
     */
    void unlock(String name);

    /**
     * @description 释放锁
     * @param rLock
     */
    void unlock(RLock rLock);

}
