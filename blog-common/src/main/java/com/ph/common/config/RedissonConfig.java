package com.ph.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penghong
 * @date 2020/9/1714:13
 */
@Configuration
public class RedissonConfig {
    /**
     *功能描述
     * @author penghong
     * @date 2020/9/17
     * @return 单点Redisson客户端
     */
    @Bean(name = "redissonclient")
    public  RedissonClient getRedissonClient(){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://203.176.95.7:6379").setPassword("123123r@");
        RedissonClient redissonClient= Redisson.create(config);
        return  redissonClient;
    }
}
