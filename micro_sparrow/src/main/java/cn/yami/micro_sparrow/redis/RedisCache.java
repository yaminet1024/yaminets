package cn.yami.micro_sparrow.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;


//TODO 实现Redis缓存
public class RedisCache implements Cache {

    //定义Redis缓存对象
    private RedisTemplate redisTemplate;
    private long defaultExpireTime = 1;
    private String name;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object o) {
        final String skey =(String) o;
        Object object = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //将key序列化
                byte[] key = redisTemplate.getKeySerializer().serialize(skey);
                byte[] value = redisConnection.get(key);
                if (value == null){
                    return null;
                }
                return redisTemplate.getValueSerializer().deserialize(value);
            }
        });
        return object!=null?new SimpleValueWrapper(object):null;
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(Object o, Object o1) {

    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    @Override
    public void evict(Object o) {

    }

    @Override
    public void clear() {

    }
}
