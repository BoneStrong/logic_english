package com.dzz.support.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis client
 *
 * @author Jeffrey
 * @since 2017/3/2 14:19
 */
@Component
public class RedisClient {

    /**
     * redis默认出队列长度
     */
    @Value("${spring.redis.custom.pop-size:200}")
    private int defaultPopSize;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis获取value
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        checkClass(value, clazz);
        return (T) value;
    }

    /**
     * redis添加数据
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * redis添加数据(设置超时)
     */
    public void set(String key, Object value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    /**
     * redis给key设置超时
     */
    public void expire(String key, long time, TimeUnit unit) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, time, unit);
        }
    }

    /**
     * 获取key的超时时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 向redis list push数据
     */
    public void rpush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从redis list pull一条数据
     */
    public <T> Optional<T> lpop(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForList().leftPop(key);
        checkClass(value, clazz);
        return Optional.ofNullable((T) value);
    }

    /**
     * 从redis list pull所有数据(根据max pop size做限制)
     */
    public <T> List<T> lpopAll(String key, Class<T> clazz) {
        return lpopAll(key, clazz, defaultPopSize);
    }

    /**
     * 从redis list pull所有数据(根据max pop size做限制)
     */
    public <T> List<T> lpopAll(String key, Class<T> clazz, int popSize) {
        List<T> list = new ArrayList<>();
        ListOperations listOperations = redisTemplate.opsForList();
        do {
            Object value = listOperations.leftPop(key);
            checkClass(value, clazz);
            if (value != null) {
                list.add((T) value);
            } else {
                break;
            }
        } while (list.size() < popSize);
        return list;
    }

    /**
     * 删除数据
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     */
    public void delete(Collection<String> key) {
        redisTemplate.delete(key);
    }

    /**
     * set中添加数据
     */
    public long setAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * set中移除数据
     */
    public long setRemove(String key, Object value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * set中查询所有成员
     */
    public <T> Set<T> setMembers(String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * set中查询是否是成员
     */
    public boolean setIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * check class
     *
     * @param value Object
     * @param clazz Class
     */
    private void checkClass(Object value, Class clazz) {
        if (value != null && value.getClass() != clazz) {
            throw new ClassCastException("can not cast class : " + clazz.getName());
        }
    }

}
