package cn.yami.micro_sparrow.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * 获取Redis的文件配置，以及构建redisPoolFactory以获得jedis
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private String timeout;


    //TODO Duration的转换


    //利用JedisPool得到jedis
    //TODO 构建工厂模式的实现
    @Bean
    public JedisPool redisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(1000);
        jedisPoolConfig.setMaxWaitMillis(0);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,3000,password);
        return jedisPool;
    }

}
