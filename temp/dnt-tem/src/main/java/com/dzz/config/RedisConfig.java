package com.dzz.config;

import com.dzz.support.profile.RedisEnv;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * redis config(cache)
 *
 * @author Jeffrey
 * @since 2017/01/10 13:03
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@RedisEnv
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * redis key prefix
     */
    @Value("${spring.redis.prefix:dnt-tem:}")
    private String redisKeyPrefix;

    /**
     * cache timeout
     */
    @Value("${spring.redis.custom.expire-time:1800}")
    private long expireTime;

    /**
     * 生成key策略
     *
     * @return KeyGenerator
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(redisKeyPrefix);
            sb.append(target.getClass().getSimpleName());
            sb.append(method.getName());
            Arrays.stream(params)
                .forEach(o -> sb.append(o == null ? "_" : "_" + o.toString()));
            return sb.toString();
        };
    }

    /**
     * 缓存管理
     *
     * @param redisTemplate RedisTemplate
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager crm = new RedisCacheManager(redisTemplate);
        crm.setDefaultExpiration(expireTime); // 设置默认缓存过期时间（秒）
        // 自定义配置缓存过期时间
        // TODO 自己添加缓存key/time
        Map<String, Long> expires = new ConcurrentHashMap<>();
        crm.setExpires(expires);
        return crm;
    }

    /**
     * cache RedisTemplate配置
     *
     * @param factory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> dnt-tem = new RedisTemplate<>();
        dnt-tem.setConnectionFactory(factory);
        // 用jackson序列化实体类
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
            new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        dnt-tem.setKeySerializer(new StringRedisSerializer());
        dnt-tem.setValueSerializer(jackson2JsonRedisSerializer);
        dnt-tem.afterPropertiesSet();
        return dnt-tem;
    }

    /**
     * string RedisTemplate配置
     *
     * @param factory RedisConnectionFactory
     * @return StringRedisTemplate
     */
    @Bean("stringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> dnt-tem = new StringRedisTemplate();
        dnt-tem.setConnectionFactory(factory);
        dnt-tem.afterPropertiesSet();
        return dnt-tem;
    }

}
