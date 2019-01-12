package cn.yami.micro_sparrow.redis;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类，用于对redis进行读写以及混淆的操作，以保证key的唯一性。
 */
@Service
public class RedisService {

    //TODO Redis连接不上异常

    @Autowired
    RedisConfig redisConfig;


    public <T> T get(String key, Class<T> value){
        Jedis jedis = null;
        try{
            jedis = redisConfig.redisPoolFactory().getResource();
            String str = jedis.get(key);
            T t = stringToBean(str,value);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    //关闭Jedis连接
    private void returnToPool(Jedis jedis) {
        if (jedis!=null){
            jedis.close();
        }
    }

    public <T> boolean set(String key,T value ){
        Jedis jedis = null;
        try{
            jedis = redisConfig.redisPoolFactory().getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0){
                return false;
            }
            jedis.set(key,str);
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    public <T> boolean set(String key,T value,int expire){
        Jedis jedis = null;
        try{
            String str = beanToString(value);
            jedis = redisConfig.redisPoolFactory().getResource();
            if (str == null || str.length() <= 0){
                return false;
            }
            jedis.setex(key,expire,str);
        }finally {
            returnToPool(jedis);
        }
        return true;
    }


    //判断当前键是否存在
    public <T> boolean exists(String key){
        Jedis jedis = null;
        try {
            jedis = redisConfig.redisPoolFactory().getResource();
            String realKey =key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }



    /**
     * 将泛型的T对象，value转换为String类型
     * @param value 泛型类
     * @return String 类型
     */
    private <T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> type = value.getClass();
        if (type == int.class || type == Integer.class){
            return String.valueOf(value);
        }else if (type == String.class){
            return (String)value;
        }else if (type == long.class || type == Long.class){
            return String.valueOf(value);
        }else {
            return JSON.toJSONString(value);
        }
    }

    //将str转换为泛型
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str,Class<T> value) {
        if (str==null || str.length()<=0){
            return null;
        }
        if (value == int.class || value == Integer.class){
            return (T)Integer.valueOf(str);
        }else if (value == String.class){
            return (T) str;
        }else if (value == long.class || value == Long.class){
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),value);
        }
    }

}
