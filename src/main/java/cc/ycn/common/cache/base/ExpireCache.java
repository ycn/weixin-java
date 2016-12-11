package cc.ycn.common.cache.base;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxToken;
import cc.ycn.common.constant.WxCacheType;
import cc.ycn.common.util.JsonConverter;
import cc.ycn.common.util.StringTool;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by andy on 12/24/15.
 */
public abstract class ExpireCache<T> {

    private final static Logger log = LoggerFactory.getLogger(ExpireCache.class);
    private final static String LOG_TAG = "[ExpireCache]";

    private WxTokenHandler wxTokenHandler;
    private Cache<String, T> cache;
    private WxCacheType cacheType;

    protected ExpireCache() {
    }

    protected void init(WxTokenHandler wxTokenHandler,
                        int expireSeconds,
                        int concurrencyLevel,
                        long maximumCacheSize,
                        WxCacheType cacheType) {

        this.wxTokenHandler = wxTokenHandler;

        // reload after expired
        this.cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumCacheSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build();

        this.cacheType = cacheType;
    }

    @SuppressWarnings("unchecked")
    final public T get(final String key) {

        final String realKey = cacheType.key(key);

        T t = null;
        try {
            t = cache.get(key, new Callable<T>() {
                @Override
                public T call() throws Exception {
                    T _t = getFromStore(key);
                    if (_t == null)
                        throw new Exception(StringTool.formatString(
                                "{} (CACHE_GET) can't get cache from store! key={}",
                                LOG_TAG, realKey));
                    return _t;
                }
            });

            log.info("{} (CACHE_GET) get cache success! key={}, value={}",
                    LOG_TAG, realKey, JsonConverter.pojo2json(t));
        } catch (Exception e) {
            log.error("{} (CACHE_GET) get cache failed! key={}",
                    LOG_TAG, realKey);
            e.printStackTrace();
        }

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

    private String getRawFromStore(String key) {
        String realKey = cacheType.key(key);
        WxToken wxToken = wxTokenHandler.get(realKey);
        if (wxToken == null) {
            log.error("{} (WX_GET) load from store failed! key={}",
                    LOG_TAG, realKey);
            return null;
        } else {
            log.info("{} (WX_GET) load from store success! key={}, value={}",
                    LOG_TAG, realKey, JsonConverter.pojo2json(wxToken));
        }
        String value = wxToken.getToken();
        if (value == null || value.isEmpty()) {
            value = null;
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    final protected T getFromStore(String key) {
        String json = getRawFromStore(key);
        Class<T> clazz = cacheType.clazz();
        return (json == null || json.isEmpty()) ? null : JsonConverter.json2pojo(json, clazz);
    }

    private void setRawToStore(String key, String token, long expiresIn) {
        String realKey = cacheType.key(key);
        Boolean result = wxTokenHandler.set(realKey, new WxToken(token, expiresIn));
        if (result == null || !result) {
            log.error("{} (WX_SET) write to store failed! {}={}, expiresIn:{}",
                    LOG_TAG, realKey, token, expiresIn);
        } else {
            log.info("{} (WX_SET) write to store success! {}={}, expiresIn:{}",
                    LOG_TAG, realKey, token, expiresIn);
            invalidate(key);
        }
    }

    final public void setToStore(String key, T t, long expiresIn) {
        String token = null;
        if (t instanceof String) {
            token = (String) t;
        } else {
            token = JsonConverter.pojo2json(t);
        }
        setRawToStore(key, token, expiresIn);
    }
}
