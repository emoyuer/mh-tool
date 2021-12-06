package com.mh.tool.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author: yjx
 * @createTime: 2021/1/5
 * @description:
 */
public interface DistributedLock {
    void lock(String key) throws DistributedLockException;

    boolean tryLock(String key) throws DistributedLockException;

    void lock(String lockKey, TimeUnit unit, int timeout) throws DistributedLockException;

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributedLockException;

    /**
     * 释放锁
     * @param lockKey
     * @throws DistributedLockException
     */
    void unlock(String lockKey) throws DistributedLockException;
}
