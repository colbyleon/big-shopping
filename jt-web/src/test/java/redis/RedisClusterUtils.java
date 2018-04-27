package redis;

import com.jt.common.util.RedisCluster;
import org.springframework.core.io.PathResource;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

public class RedisClusterUtils {
    private static JedisCluster jedisCluster;

    private RedisClusterUtils(){}

    public static JedisCluster getJedisCluster(){

        if (jedisCluster == null){
            try {
                RedisCluster redisCluster = new RedisCluster();
                PathResource pathResource = new PathResource(
                        RedisClusterUtils.class.getClassLoader().getResource(
                                "properties/redis.properties").toString().substring(6));
                redisCluster.setAddressConfig(pathResource);
                redisCluster.setAddressKeyPrefix("redis.cluster");
                redisCluster.setMaxRedirections(6);
                redisCluster.setTimeout(1000);
                redisCluster.setGenericObjectPoolConfig(getConfig());
                redisCluster.afterPropertiesSet();
                jedisCluster = redisCluster.getObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jedisCluster;
    }

    private static JedisPoolConfig getConfig() throws IOException {
        Properties prop = new Properties();
        prop.load(RedisClusterUtils.class.getClassLoader().getResourceAsStream("properties/redis.properties"));

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("redis.maxIdle")));
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(prop.getProperty("redis.maxWait")));
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("redis.maxTotal")));
        jedisPoolConfig.setMinIdle(Integer.parseInt(prop.getProperty("redis.minldle")));
        return jedisPoolConfig;
    }
}
