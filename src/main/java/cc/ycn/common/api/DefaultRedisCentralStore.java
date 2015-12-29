package cc.ycn.common.api;

import cc.ycn.common.util.RedisDb;

/**
 * Created by andy on 12/29/15.
 */
public class DefaultRedisCentralStore implements CentralStore {

    private RedisDb redis;

    public DefaultRedisCentralStore(String host, int port, int db) {
        redis = new RedisDb(host, port, db);
    }

    @Override
    public void set(String key, String val) {
        redis.set(key, val);
    }

    @Override
    public void set(String key, String val, long seconds) {
        redis.set(key, val, (int) seconds);
    }

    @Override
    public String get(String key) {
        return redis.get(key);
    }

    @Override
    public void del(String key) {
        redis.del(key);
    }
}
