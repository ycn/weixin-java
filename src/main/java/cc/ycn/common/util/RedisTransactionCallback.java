package cc.ycn.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by andy on 7/25/15.
 */
public interface RedisTransactionCallback {

    public void beforeTransaction(Jedis client, Object context);

    public void run(Transaction transaction, Object context);

    public void onSuccess(TransactionStatus status, Object context, List<Object> result);

    public void onFailed(TransactionStatus status, Object context, Throwable throwable);

    public void afterTransaction(Jedis client, Object context);
}
