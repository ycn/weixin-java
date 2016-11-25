package cc.ycn.common.cache.base;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxToken;
import cc.ycn.common.constant.WxCacheType;
import cc.ycn.common.util.JsonConverter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by andy on 12/24/15.
 */
public abstract class ExpireCache<T> {

    private final static Logger log = LoggerFactory.getLogger(ExpireCache.class);
    private final static String LOG_TAG = "[ExpireCache]";

    private WxTokenHandler wxTokenHandler;
    private LoadingCache<String, T> cache;
    private WxCacheType cacheType;

    protected ExpireCache() {
    }

    protected void init(WxTokenHandler wxTokenHandler,
                        int refreshSeconds,
                        int concurrencyLevel,
                        long maximumCacheSize,
                        WxCacheLoader<T> cacheLoader,
                        WxCacheType cacheType) {

        this.wxTokenHandler = wxTokenHandler;

        // reload after expired
        this.cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumCacheSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(cacheLoader);

        this.cacheType = cacheType;
    }

    final public T get(String key) {
        T t = cache.getUnchecked(key);
        if (t instanceof String && ((String) t).isEmpty()) {
            invalidate(key);
        }
        return t;
    }

    final public void invalidate(String key) {
        cache.invalidate(key);
    }

    final public T refresh(String key) {
        invalidate(key);
        return get(key);
    }

    final protected String getFromStore(String key) {
        String realKey = cacheType.key(key);
        WxToken wxToken = wxTokenHandler.get(realKey);
        if (wxToken == null) {
            return null;
        }
        String value = wxToken.getToken();
        if (value == null || value.isEmpty()) {
            value = null;
        }
        return value;
    }

    final protected T getFromStore(String key, Class<T> clazz) {
        String json = getFromStore(key);
        return (json == null || json.isEmpty()) ? null : JsonConverter.json2pojo(json, clazz);
    }

    final public void setToStore(String key, String token, long expiresIn) {
        String realKey = cacheType.key(key);
        Boolean result = wxTokenHandler.set(realKey, new WxToken(token, expiresIn));
        if (result == null || !result) {
            log.error("{} (WX_SET) set failed! {}={}, expiresIn:{}", LOG_TAG, realKey, token, expiresIn);
        } else {
            log.info("{} (WX_SET) set success! {}={}, expiresIn:{}", LOG_TAG, realKey, token, expiresIn);
        }
    }
}
