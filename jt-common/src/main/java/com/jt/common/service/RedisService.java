package com.jt.common.service;

import com.jt.common.util.RedisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {

    /**
     * requeired=false只在用的时候才注入
     * 原因：工具类可以被很多程序进行引用，如果该属性在某个模块不用使用也不配置
     * 则用了这个工具类就会启动报错
     */
    /*@Autowired(required = false) //调用时才会自动注入
    private StringRedisTemplate redisTemplate;


    //插入数据
    public boolean set(String key,String value){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String get(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }*/

    /*//实现分片的redis操作
    @Autowired
    ////private ShardedJedisPool shardedJedisPool;

    public void set(String key, String value) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        jedis.set(key, value);
    }

    public String get(String key) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.get(key);
    }*/


    /**
     * 实现哨兵的操作
     */
   /* @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    public void set(String key, String value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }

    public String get(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(key);
        return value;
    }*/

   /* @Autowired
    private JedisCluster jedisCluster;

    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }*/
}
