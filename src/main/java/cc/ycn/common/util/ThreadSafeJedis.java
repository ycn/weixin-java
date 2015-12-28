/**
 Contributors: Nachi
 */
package cc.ycn.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreadSafeJedis {

    private static final int DEFAULT_SOCKET_TIMEOUT = 2000;
    private static final int DEFAULT_MAX_CLIENTS = 500;
    protected JedisPool objectPool;
    protected int db = 0;

    public ThreadSafeJedis(String host, int port, int db) {
        objectPool = new JedisPool(getDefaultConfig(), host, port, DEFAULT_SOCKET_TIMEOUT);
        this.db = db;
    }

    public ThreadSafeJedis(String host, int port, String password, int db) {
        objectPool = new JedisPool(getDefaultConfig(), host, port, DEFAULT_SOCKET_TIMEOUT, password);
        this.db = db;
    }

    public ThreadSafeJedis(String host, int port, int timeout, int db) {
        objectPool = new JedisPool(getDefaultConfig(), host, port, timeout);
        this.db = db;
    }

    public ThreadSafeJedis(String host, int port, int timeout, String password, int db) {
        objectPool = new JedisPool(getDefaultConfig(), host, port, timeout, password);
        this.db = db;
    }

    public ThreadSafeJedis(JedisPoolConfig poolConfig, String host, int port, int db) {
        objectPool = new JedisPool(poolConfig, host, port, DEFAULT_SOCKET_TIMEOUT);
        this.db = db;
    }

    public ThreadSafeJedis(JedisPoolConfig poolConfig, String host, int port, int timeout, int db) {
        objectPool = new JedisPool(poolConfig, host, port, timeout);
        this.db = db;
    }

    public ThreadSafeJedis(JedisPoolConfig poolConfig, String host, int port, int timeout, String password, int db) {
        objectPool = new JedisPool(poolConfig, host, port, timeout, password);
        this.db = db;
    }

    private JedisPoolConfig getDefaultConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(DEFAULT_MAX_CLIENTS);
        return poolConfig;
    }

    public String set(final String key, String value) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.set(key, value);
        client.close();
        return response;
    }

    public String setex(final String key, int seconds, String value) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.setex(key, seconds, value);
        client.close();
        return response;
    }

    public String get(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.get(key);
        client.close();
        return response;
    }

    public Boolean exists(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Boolean response = client.exists(key);
        client.close();
        return response;
    }

    public Long del(final String... keys) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.del(keys);
        client.close();
        return response;
    }

    public Long del(String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.del(key);
        client.close();
        return response;
    }

    public String type(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.type(key);
        client.close();
        return response;
    }

    public Set<String> keys(final String pattern) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Set<String> response = client.keys(pattern);
        client.close();
        return response;
    }

    public String randomKey() {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.randomKey();
        client.close();
        return response;
    }

    public String rename(final String oldkey, final String newkey) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.rename(oldkey, newkey);
        client.close();
        return response;
    }

    public Long renamenx(final String oldkey, final String newkey) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.renamenx(oldkey, newkey);
        client.close();
        return response;
    }

    public Long expire(final String key, final int seconds) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.expire(key, seconds);
        client.close();
        return response;
    }

    public Long expireAt(final String key, final long unixTime) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.expireAt(key, unixTime);
        client.close();
        return response;
    }

    public Long ttl(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.ttl(key);
        client.close();
        return response;
    }

    public Long incr(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.incr(key);
        client.close();
        return response;
    }

    public Long incrBy(final String key, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.incrBy(key, amount);
        client.close();
        return response;
    }

    public Long incrByFailOnZero(final String key, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String strVal = client.get(key);
        long val = 0;
        try {
            val = Long.parseLong(strVal);
        } catch (NumberFormatException ignore) {
        }
        if (val + amount < 0) {
            throw new RuntimeException("incr to zero");
        }
        Long response = client.decr(key);
        client.close();
        return response;
    }

    public Long decr(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.decr(key);
        client.close();
        return response;
    }

    public Long decrBy(final String key, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.decrBy(key, amount);
        client.close();
        return response;
    }

    public Long decrFailOnZero(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String strVal = client.get(key);
        long val = 0;
        try {
            val = Long.parseLong(strVal);
        } catch (NumberFormatException ignore) {
        }
        if (val - 1 < 0) {
            throw new RuntimeException("decr to zero");
        }
        Long response = client.decr(key);
        client.close();
        return response;
    }

    public Long decrByFailOnZero(final String key, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String strVal = client.get(key);
        long val = 0;
        try {
            val = Long.parseLong(strVal);
        } catch (NumberFormatException ignore) {
        }
        if (val - amount < 0) {
            throw new RuntimeException("decr to zero");
        }
        Long response = client.decr(key);
        client.close();
        return response;
    }

    public Long hset(final String key, final String field, final String value) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.hset(key, field, value);
        client.close();
        return response;
    }

    public String hget(final String key, final String field) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.hget(key, field);
        client.close();
        return response;
    }

    public Long hincrBy(final String key, final String field, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.hincrBy(key, field, amount);
        client.close();
        return response;
    }

    public Long hincrByFailOnZero(final String key, final String field, final long amount) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String strVal = client.get(key);
        long val = 0;
        try {
            val = Long.parseLong(strVal);
        } catch (NumberFormatException ignore) {
        }
        if (val + amount < 0) {
            throw new RuntimeException("hincr to zero");
        }
        Long response = client.hincrBy(key, field, amount);
        client.close();
        return response;
    }

    public Boolean hexists(final String key, final String field) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Boolean response = client.hexists(key, field);
        client.close();
        return response;
    }

    public Long hdel(final String key, final String... fields) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.hdel(key, fields);
        client.close();
        return response;
    }

    public Long hlen(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.hlen(key);
        client.close();
        return response;
    }

    public Set<String> hkeys(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Set<String> response = client.hkeys(key);
        client.close();
        return response;
    }

    public Map<String, String> hgetAll(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Map<String, String> response = client.hgetAll(key);
        client.close();
        return response;
    }

    public Long rpush(final String key, final String... strings) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.rpush(key, strings);
        client.close();
        return response;
    }

    public Long lpush(final String key, final String... strings) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.lpush(key, strings);
        client.close();
        return response;
    }

    public Long llen(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.llen(key);
        client.close();
        return response;
    }

    public List<String> lrange(final String key, final long start,
                               final long end) {
        Jedis client = objectPool.getResource();
        client.select(db);
        List<String> response = client.lrange(key, start, end);
        client.close();
        return response;
    }

    public String lpop(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.lpop(key);
        client.close();
        return response;
    }

    public String rpop(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.rpop(key);
        client.close();
        return response;
    }

    public Long sadd(final String key, final String... members) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.sadd(key, members);
        client.close();
        return response;
    }

    public Set<String> smembers(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Set<String> response = client.smembers(key);
        client.close();
        return response;
    }

    public Long srem(final String key, final String... members) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.srem(key, members);
        client.close();
        return response;
    }

    public Long scard(final String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.scard(key);
        client.close();
        return response;
    }

    public Boolean sismember(final String key, final String member) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Boolean response = client.sismember(key, member);
        client.close();
        return response;
    }

    public Set<String> sunion(final String... keys) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Set<String> response = client.sunion(keys);
        client.close();
        return response;
    }

    public Long sunionstore(final String dstkey, final String... keys) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.sunionstore(dstkey, keys);
        client.close();
        return response;
    }

    public String watch(final String... keys) {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.watch(keys);
        client.close();
        return response;
    }

    public void close() {
        objectPool.destroy();
    }

    public List<String> blpop(int timeout, String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        List<String> response = client.blpop(key, String.valueOf(timeout));
        client.close();
        return response;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis client = objectPool.getResource();
        client.select(db);
        List<String> response = client.brpop(key, String.valueOf(timeout));
        client.close();
        return response;
    }

    public String ping() {
        Jedis client = objectPool.getResource();
        client.select(db);
        String response = client.ping();
        client.close();
        return response;
    }

    public Long publish(String channel, String message) {
        Jedis client = objectPool.getResource();
        client.select(db);
        Long response = client.publish(channel, message);
        client.close();
        return response;
    }

    public Jedis getClient() {
        Jedis client = objectPool.getResource();
        client.select(db);
        return client;
    }

    public void close(Jedis client) {
        client.close();
    }
}