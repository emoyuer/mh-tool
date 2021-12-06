package com.mh.tool.lock.utils;

import com.mh.tool.lock.DistributedLock;
import com.mh.tool.lock.DistributedLockException;

import java.util.concurrent.TimeUnit;

/**
 * @author: yjx
 * @createTime: 2021/1/5
 * @description:
 */
public class MhLockUtil {

    private static DistributedLock distributedLock;

    public static void setLocker(DistributedLock locker) {
        distributedLock = locker;
    }

    /**
     *
     * @param key rediskey
     */
    public static void lock(String key) {
        try {
            distributedLock.lock(key);
        } catch (DistributedLockException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param lockKey redis key
     * @param unit 时间单位
     * @param leaseTime 锁的超时时间
     */
    public static void lock(String lockKey, TimeUnit unit, int leaseTime) {
        try {
            distributedLock.lock(lockKey,unit,leaseTime);
        } catch (DistributedLockException e) {
            e.printStackTrace();
        }
    }

    public static void unlock(String lockKey) {
        try {
            distributedLock.unlock(lockKey);
        } catch (DistributedLockException e) {
            e.printStackTrace();
        }
    }

    public static boolean tryLock(String key) {
        try {
            return distributedLock.tryLock(key);
        } catch (DistributedLockException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime){
        try {
            return distributedLock.tryLock(lockKey,unit,waitTime,leaseTime);
        } catch (DistributedLockException e) {
            e.printStackTrace();
        }
        return false;
    }
}
