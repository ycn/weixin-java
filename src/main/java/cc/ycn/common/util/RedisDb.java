package cc.ycn.common.util;


import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 6/9/15.
 */
public class RedisDb {
    private static final Logger log = LoggerFactory.getLogger(RedisDb.class);

    private int db = 0;
    private int retry = 0;
    private ThreadSafeJedis safeJedis;


    public RedisDb(String host, int port, int db) {
        this.db = db > 0 ? db : 0;
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        safeJedis = new ThreadSafeJedis(poolConfig, host, port, db);
    }

    public int getDb() {
        return db;
    }

    public int getRetry() {
        return retry;
    }

    public long del(String key) {
        long resp = 0;
        try {
            resp = safeJedis.del(key);
        } catch (JedisException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public String get(String key) {
        String value = null;
        try {
            value = safeJedis.get(key);
            // unicode转换中文
            value = StringEscapeUtils.unescapeJava(value);
        } catch (JedisException e) {
            e.printStackTrace();
        }
        return value;
    }

    public Map<String, String> getAll(Iterable<? extends String> keys) {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : keys) {
            map.put(key, get(key));
        }
        return map;
    }

    public RedisDb set(String key, String value) {
        return set(key, value, 0);
    }

    public RedisDb set(String key, String value, int seconds) {
        try {
            // 中文转换unicode
            value = StringEscapeUtils.escapeJava(value);
            if (seconds > 0)
                safeJedis.setex(key, seconds, value);
            else
                safeJedis.set(key, value);
        } catch (JedisException ignore) {
        }
        return this;
    }

    public RedisDb rawSet(String key, String value) {
        return rawSet(key, value, 0);
    }

    public RedisDb rawSet(String key, String value, int seconds) {
        try {
            if (seconds > 0)
                safeJedis.setex(key, seconds, value);
            else
                safeJedis.set(key, value);
        } catch (JedisException ignore) {
        }
        return this;
    }

    public long incr(String key) {
        long result = 0;

        try {
            result = safeJedis.incr(key);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long incrByFailOnZero(String key, long amount) {
        long result = 0;

        try {
            result = safeJedis.incrByFailOnZero(key, amount);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long decr(String key) {
        long result = 0;

        try {
            result = safeJedis.decr(key);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long hincrBy(String key, String field, long amount) {
        long result = 0;

        try {
            result = safeJedis.hincrBy(key, field, amount);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long hincrByFailOnZero(String key, String field, long amount) {
        long result = 0;

        try {
            result = safeJedis.hincrByFailOnZero(key, field, amount);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public Map<String, String> hgetall(String key) {
        Map<String, String> result = new HashMap<String, String>();

        try {
            result = safeJedis.hgetAll(key);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long hset(String key, String field, String value) {
        long result = 0;
        try {
            // 中文转换unicode
            value = StringEscapeUtils.escapeJava(value);
            result = safeJedis.hset(key, field, value);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public String hget(String key, String field) {
        String value = null;
        try {
            value = safeJedis.hget(key, field);
        } catch (JedisException ignore) {
        }
        return value;
    }

    public long dailyIncr(String key) {
        long result = 0;

        try {
            result = safeJedis.incr(key);
            // set delayTime timestamp
            if (result <= 1) {
                long tomorrowTime = DateTool.getTomorrowTime();
                safeJedis.expireAt(key, tomorrowTime / 1000);
            }
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long dailyDecr(String key) {
        long result = 0;

        try {
            result = safeJedis.decr(key);
            // set delayTime timestamp
            if (result < 1) {
                safeJedis.set(key, "0");
            }
        } catch (JedisException ignore) {
        }
        return result;
    }

    public long rpush(String key, String... vals) {
        long result = 0;

        try {
            result = safeJedis.rpush(key, vals);
        } catch (JedisException ignore) {
        }
        return result;
    }

    public Jedis getClient() {
        return safeJedis.getClient();
    }

    public void destroy() {
        safeJedis.close();
    }

    public long publish(String channel, String message) {
        long result = 0;

        try {
            result = safeJedis.publish(channel, message);
        } catch (JedisException ignore) {
        }
        return result;
    }


    public void doSubscribe(String[] channels, JedisPubSub jedisPubSub) {

        if (channels == null || channels.length <= 0)
            return;

        Jedis client = safeJedis.getClient();
        client.subscribe(jedisPubSub, channels);
    }


    public void doTransaction(String[] lockKeys, RedisTransactionCallback callback, Object context, int retryTimes) {

        boolean retry = false;
        boolean skip = false;
        Jedis client = safeJedis.getClient();


        try {
            callback.beforeTransaction(client, context);
        } catch (Exception e) {
            log.warn("beforeTransaction failed: {}, {}", DebugTool.getCallerName(e), e.getMessage());
            callback.afterTransaction(client, context);
            client.close();
            retry = true;
            skip = true;
        }

        // 加锁
        if (lockKeys != null && lockKeys.length > 0)
            client.watch(lockKeys);

        if (!skip) {
            // 事务开始
            Transaction transaction = client.multi();

            try {

                callback.run(transaction, context);

                // 处理结果
                List<Object> result = transaction.exec();

                if (result == null) {
                    callback.onFailed(TransactionStatus.WATCH_FAILED,
                            context,
                            new RuntimeException("failed on duplicate request"));

                    retry = true;

                } else {
                    callback.onSuccess(TransactionStatus.SUCCESS, context, result);
                }

            } catch (Exception e) {

                log.error("redis transaction exception, {}: {}", retryTimes, e.getMessage());

                try {
                    transaction.discard();
                } catch (Exception ignore) {
                }

                callback.onFailed(TransactionStatus.FAILED, context, e);

                retry = true;

            } finally {

                callback.afterTransaction(client, context);

                // 解锁, 事务结束
                client.close();
            }
        }


        // 重试
        if (retry && retryTimes > 0) {
            doTransaction(lockKeys, callback, context, --retryTimes);
        }
    }
}
