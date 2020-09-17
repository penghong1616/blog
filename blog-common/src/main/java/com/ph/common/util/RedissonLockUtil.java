package com.ph.common.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author penghong
 * @date 2020/9/1713:33
 * @describe Redission锁
 */
public class RedissonLockUtil {
    private static RedissonClient redissonClient;
    @Resource
    private RedissonClient redissonclient;
    public  RedissonLockUtil(){
        redissonClient=redissonclient;
    }
    /**
     * 拿不到lock就不罢休，不然线程就一直block
     */
    public static RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * leaseTime为加锁时间，单位为毫秒
     */
    public static RLock lock(String lockKey, long leaseTime) {
        return lock(lockKey,leaseTime, TimeUnit.MILLISECONDS);
    }

    /**
     * timeout为加锁时间，时间单位由unit确定
     */
    public static RLock lock(String lockKey, long timeout, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取重入锁，带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     * @param lockKey
     * @param waitTime 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
     * @param leaseTime 锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完)
     * @param unit 时间单位
     */
    public static boolean tryLock(String lockKey,long waitTime, long leaseTime,TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 如果该重入锁被其他线程持有,会等待waitTime毫秒,然后返回是否成功,如果成功会在leaseTime毫秒后自动解锁
     * @param lockKey
     * @param waitTime 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
     * @param leaseTime 锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完)

     */
    public static boolean tryLock(String lockKey,long waitTime, long leaseTime) {
        return tryLock(lockKey,waitTime, leaseTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 如果该重入锁被其他线程持有,直接返回false
     * @param lockKey
     */
    public static boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取重入锁，等待waitTime毫秒后拿不到lock，返回false.
     * @param lockKey
     * @param lessTime 锁的过期时间
     */
    public static boolean tryLock(String lockKey, long lessTime) {
        return tryLock(lockKey,lessTime,TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取可重入锁，等待waitTime后拿不到lock，返回false.
     * @param lockKey
     * @param lessTime 锁的过期时间
     * @param unit 时间单位
     * @return
     */
    public static boolean tryLock(String lockKey, long lessTime,TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(1,lessTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取重入锁，等待waitTime毫秒后拿不到lock，返回false.
     * @param lockKey
     * @param waitTime 锁的等待时间
     * @return
     */
    public static boolean tryLockWait(String lockKey, long waitTime) {
        return tryLockWait(lockKey,waitTime,TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取可重入锁，等待waitTime后拿不到lock，返回false.
     * @param lockKey
     * @param waitTime 锁的等待时间
     * @param unit 时间单位
     * @return
     */
    public static boolean tryLockWait(String lockKey, long waitTime,TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取公平锁，等待waitTime后拿不到lock，返回false.
     * @param lockKey
     * @param waitTime 锁的等待时间
     * @param unit 时间单位
     * @return
     */
    public static boolean tryFairLock(String lockKey, long waitTime,TimeUnit unit) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取公平锁，等待waitTime毫秒后拿不到lock，返回false.
     * @param lockKey
     * @param waitTime 锁的等待时间
     */
    public static boolean tryFairLock(String lockKey, long waitTime) {
        return tryFairLock(lockKey,waitTime,TimeUnit.MILLISECONDS);
    }

    /**
     * 如果该公平锁被其他线程持有,直接返回false
     * @param lockKey
     */
    public static boolean tryFairLock(String lockKey) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取公平锁，带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     * @param lockKey
     * @param waitTime 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
     * @param leaseTime 锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完)
     * @param unit 时间单位
     */
    public static boolean tryFairLock(String lockKey,long waitTime, long leaseTime,TimeUnit unit) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 如果该公平锁被其他线程持有,会等待waitTime毫秒,然后返回是否成功,如果成功会在leaseTime毫秒后自动解锁
     * @param lockKey
     * @param waitTime 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
     * @param leaseTime 锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完)
     */
    public static boolean tryFairLock(String lockKey,long waitTime, long leaseTime) {
        return tryLock(lockKey,waitTime, leaseTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放 key 的锁
     */
    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        unlock(lock);
    }

    /**
     * 释放锁
     */
    public static void unlock(RLock lock) {
        if(lock.isLocked()) {
            lock.unlock();
        }
    }

}
