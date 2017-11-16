package org.matrixstudio.boot.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootConfiguration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 缓存过期时间 60 秒
        cacheManager.setDefaultExpiration(60);
        return cacheManager;
    }
}
