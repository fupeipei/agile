package com.yusys.agile.redis.service.impl;

import com.yusys.agile.redis.service.RedissonService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description redisson业务实现类
 *  
 * @date 2020/04/26
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonServiceImpl.class);

    @Autowired
    private RedissonClient redissonSentinel;

    @Override
    public RLock getLock(String name) {
        RLock rLock = redissonSentinel.getLock(name);
        return rLock;
    }

    @Override
    public RLock lock(String name) {
        RLock rLock = redissonSentinel.getLock(name);
        rLock.lock();
        return rLock;
    }

    @Override
    public RLock lock(String name, long leaseTime) {
        RLock rLock = redissonSentinel.getLock(name);
        rLock.lock(leaseTime, TimeUnit.SECONDS);
        return rLock;
    }

    @Override
    public RLock lock(String name, long leaseTime, TimeUnit unit) {
        RLock rLock = redissonSentinel.getLock(name);
        rLock.lock(leaseTime, unit);
        return rLock;
    }

    @Override
    public boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit) {
        RLock rLock = redissonSentinel.getLock(name);
        try {
            return rLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            LOGGER.error("tryLock method occur exception:{}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit unit, int retryTimes) {
        boolean result = false;
        if (retryTimes > 0) {
            for (int i = 0; i < retryTimes; i++) {
                if (this.tryLock(name, waitTime, leaseTime, unit)) {
                    result = true;
                    break;
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        result = false;
                        LOGGER.error("tryLock method sleep occur exception:{}", e.getMessage());
                    }
                }
            }
        } else {
            result = this.tryLock(name, waitTime, leaseTime, unit);
        }
        return result;
    }

    @Override
    public void unlock(String name) {
        RLock rLock = redissonSentinel.getLock(name);
        rLock.unlock();
    }

    @Override
    public void unlock(RLock rLock) {
        rLock.unlock();
    }
}
