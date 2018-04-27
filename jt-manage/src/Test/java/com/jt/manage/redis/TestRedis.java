package com.jt.manage.redis;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestRedis {

//    @Test
//    public void test01(){
//        Jedis jedis = new Jedis("192.168.88.134", 6379);
//        jedis.set("1711", "火山小视频");
//        System.out.println(jedis.get("1711"));
//    }
//
//    /**
//     * 采用分片实现数据的缓存
//     */
//    @Test
//    public void test02() {
//        //1. 池对象配置
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(1000);
//        config.setMaxIdle(100);
//        config.setTestOnBorrow(true); //写入先测试能否写入,不能就换一个redis连接
//
//        //2. 定义分片的list集合
//        List<JedisShardInfo> infoList = new ArrayList<>();
//
//        //3. 添加分片的list
//        infoList.add(new JedisShardInfo("192.168.88.134", 6379));
//        infoList.add(new JedisShardInfo("192.168.88.134", 6380));
//        infoList.add(new JedisShardInfo("192.168.88.134", 6381));
//
//        //4. 获取池对象
//        ShardedJedisPool jedisPool = new ShardedJedisPool(config,infoList);
//
//        //5. 获取jedis对象
//        ShardedJedis shardedJedis = jedisPool.getResource();
//        shardedJedis.set("name", "tomCat");
//        System.out.println("your name is " + shardedJedis.get("name"));
//        jedisPool.returnResource(shardedJedis);
//    }
//
//    @Test
//    public void test03(){
//        // 定义set集合
//        Set<String> sentinels = new HashSet<>();
//        sentinels.add(new HostAndPort("192.168.88.134", 26379).toString());
//        sentinels.add(new HostAndPort("192.168.88.134", 26380).toString());
//        sentinels.add(new HostAndPort("192.168.88.134", 26381).toString());
//        System.out.println("信息：" + new HostAndPort("192.168.88.134", 26379).toString());
//        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
//        Jedis jedis = sentinelPool.getResource();
//        jedis.set("name", "快手大表哥");
//        System.out.println(jedis.get("name"));
//    }
}
