package com.ph.common.servcie;

import com.ph.common.config.RedissonConfig;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存数据操作类
 */
@Service
public class RedissonService {

    @Resource(name = "redissonclient")
    private  RedissonClient redissonClient;

    //-------------------------------------操作对象--------------------------------------//
    /**
     * 获取对象值
     *
     * @param name
     * @param
     * @return <T> T
     */
    public <T> T getValue(String name) {
        RBucket<T> bucket = redissonClient.getBucket(name);
        return bucket.get();
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getBucket(String name) {
        return redissonClient.getBucket(name);
    }

    /**
     * 设置对象的值,默认永久
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public <T> void setValue(String name, T value) {
        setValue(name,value,-1);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒  -1 永久缓存
     * @return
     */
    public <T> void setValue(String name, T value, long time) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        if(time==-1){
            bucket.set(value);
        }else {
            bucket.set(value, time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 如果值已经存在则不设置
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> boolean trySetValue(String name, T value, long time) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        boolean b;
        if(time==-1){
            b = bucket.trySet(value);
        }else {
            b = bucket.trySet(value, time, TimeUnit.MILLISECONDS);
        }
        return b;
    }

    /**
     * 如果值已经存在则不设置
     *
     * @param name  键
     * @param value 值
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> boolean trySetValue(String name, T value) {
        return trySetValue(name,value,-1);
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public boolean delete(String name) {
        return redissonClient.getBucket(name).delete();
    }

    /**
     * 是否存在 name
     * @param name
     * @return
     */
    public boolean isExistsObject(String name){
        RBucket<Object> bucket = redissonClient.getBucket(name);
        return bucket.isExists();
    }

    /**
     * 设置指定 key 的值,并返回 key 的旧值
     * @param name value
     * @param value 新值
     * @return <T> T
     */
    public <T> T getset(String name, T value) {
        RBucket<T> bucket = redissonClient.getBucket(name);
        return bucket.getAndSet(value);
    }

    /**
     * 设置指定 key 的值,并返回 key 的旧值
     * @param name value time
     * @param value 新值
     * @param time 过期时间,单位毫秒
     * @return
     */
    public <T> T getset(String name, T value, long time) {
        RBucket<T> bucket = redissonClient.getBucket(name);
        return bucket.getAndSet(value);
    }

    /**
     * 获取过期时间
     * @param name
     * @return
     */
    public long getTtl(String name){
        RKeys rkeys = redissonClient.getKeys();
        return rkeys.remainTimeToLive(name);
    }

    //-------------------------------------操作集合--------------------------------------//
    /**
     * 获取map集合
     *
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMap<K, V> getMap(String name) {
        return redissonClient.getMap(name);
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setMapValues(String name, Map data) {
        setMapValues(name,data,-1);
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setMapValues(String name, Map data,long lessTime) {
        RMap map = redissonClient.getMap(name);
        map.putAll(data);
        if (lessTime != -1) {
            map.expire(lessTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 设置MapCache集合,默认永久,不存在则赋值
     * @param name
     * @param data
     */
    public void setMapCacheIfAbsent(String name,  String key,Object data) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(name);
        mapCache.putIfAbsent(key,data);
    }

    /**
     * 设置MapCache集合,不存在则赋值
     * @param name
     * @param data
     * @param time 元素过期时间，默认毫秒
     */
    public void setMapCacheIfAbsent(String name, String key,Object data,long time) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(name);
        mapCache.putIfAbsent(key,data,time,TimeUnit.MILLISECONDS);
    }

    /**
     * 设置MapCache集合,默认永久
     * @param name
     * @param data
     */
    public void setMapCacheValues(String name, Map data) {
        RMapCache<String, String> mapCache = redissonClient.getMapCache(name);
        mapCache.putAll(data);
    }

    /**
     * 设置MapCache集合
     * @param name
     * @param data
     * @param time 元素过期时间，默认毫秒
     */
    public void setMapCacheValues(String name, Map data,long time) {
        RMapCache<String, String> mapCache = redissonClient.getMapCache(name);
        mapCache.putAll(data);
    }

    /**
     * 获取MapCache集合
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMapCache<K, V> getMapCache(String name) {
        return redissonClient.getMapCache(name);
    }

    /**
     * 本地缓存映射,默认配置
     * @param name
     * @param data
     */
    public void setLocalMapValues(String name, Map data) {
        RLocalCachedMap<String, Object> localMap = redissonClient.getLocalCachedMap(name, LocalCachedMapOptions.defaults());
        localMap.putAll(data);
    }

    /**
     * 本地缓存映射,默认配置
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RLocalCachedMap<K, V> geLocaltMap(String name) {
        return redissonClient.getLocalCachedMap(name, LocalCachedMapOptions.defaults());
    }

    /**
     * 本地缓存映射
     * @param name
     * @param data
     * @param options 自定义配置
     */
    public void setLocalMapValues(String name, Map data,LocalCachedMapOptions options) {
        RLocalCachedMap<String, Object> localMap = redissonClient.getLocalCachedMap(name, options);
        localMap.putAll(data);
    }

    /**
     * 本地缓存映射
     * @param name
     * @param options 自定义配置
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RLocalCachedMap<K, V> geLocaltMap(String name,LocalCachedMapOptions options) {
        return redissonClient.getLocalCachedMap(name, options);
    }

    /**
     * 获取List集合
     *
     * @param name
     * @return
     */
    public <T> RList<T> getList(String name) {
        return redissonClient.getList(name);
    }

    /**
     * 设置List集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setListValues(String name, List data, long time) {
        RList list = redissonClient.getList(name);
        list.addAll(data);
        if (time != -1) {
            list.expire(time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 设置List集合,默认永久
     *
     * @param name
     * @param data
     * @return
     */
    public void setListValues(String name, List data) {
        setListValues(name, data,-1);
    }

    /**
     * 获取set集合
     *
     * @param name
     * @return
     */
    public <T> RSet<T> getSet(String name) {
        return redissonClient.getSet(name);
    }

    public <T> RScoredSortedSet<T> getSortSet(String name) {
        return redissonClient.getScoredSortedSet(name);
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setSetValues(String name, Set data, long time) {
        RSet set = redissonClient.getSet(name);
        set.addAll(data);
        if (time != -1) {
            set.expire(time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setSetValues(String name, Set data) {
        setSetValues(name, data, -1);
    }

    /**
     *
     * @param name map名称
     * @param key
     * @return
     */
    public boolean containsKey(String name,String key){
        RMap map = redissonClient.getMap(name);
        return map.containsKey(key);
    }

    //-------------------------------------操作对象二进制--------------------------------------//
    /**
     * 获取输出流
     * @param name
     * @return
     */
    public OutputStream getOutputStream(String name) {
        RListMultimap<Object, Object> listMultimap = redissonClient.getListMultimap("");
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.getOutputStream();
    }
    /**
     * 获取输入流
     * @param name
     * @return
     */
    public InputStream getInputStream(String name) {
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.getInputStream();
    }
    /**
     * 获取输入流
     * @param name
     * @return
     */
    public InputStream getValue(String name,OutputStream stream) {
        try {
            RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
            InputStream inputStream = binaryStream.getInputStream();
            byte[] buff=new byte[1024];
            int len;
            while ((len=inputStream.read(buff))!=-1){
                stream.write(buff,0,len);
            }
            return binaryStream.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @return
     */
    public RBinaryStream getBinaryStream(String name) {
        return redissonClient.getBinaryStream(name);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public void setValue(String name, InputStream value) {
        try {
            RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
            binaryStream.delete();
            OutputStream outputStream = binaryStream.getOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len=value.read(buff))!=-1){
                outputStream.write(buff,0,len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public boolean deleteBinaryStream(String name) {
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.delete();
    }

}