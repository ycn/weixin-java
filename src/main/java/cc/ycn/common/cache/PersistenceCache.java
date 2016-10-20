package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.util.JsonConverter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by andy on 12/24/15.
 */
public abstract class PersistenceCache<T> {

    private final static Logger log = LoggerFactory.getLogger(PersistenceCache.class);
    private final static String LOG_TAG = "[PersistenceCache]";

    private CentralStore centralStore;
    private LoadingCache<String, T> cache;
    private String keyPrefix;
    private boolean readonly;

    protected PersistenceCache() {

    }

    protected void init(CentralStore centralStore,
                        int refreshSeconds,
                        int concurrencyLevel,
                        long maximumCacheSize,
                        WxCacheLoader<T> cacheLoader,
                        CacheKeyPrefix keyPrefix,
                        boolean readonly) {

        this.centralStore = centralStore;

        // reload after expired
        this.cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumCacheSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(cacheLoader);

        this.keyPrefix = keyPrefix.prefix();
        this.readonly = readonly;
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

    final public void setToStore(String key, T value) {
        if (readonly)
            return;
        if (key == null || key.isEmpty())
            return;
        if (value == null)
            return;

        if (value instanceof String) {
            centralStore.set(keyPrefix + key, (String) value);
        } else {
            centralStore.set(keyPrefix + key, JsonConverter.pojo2json(value));
        }

        cache.invalidate(key);
    }

    final public void delFromStore(String key) {
        if (readonly)
            return;
        centralStore.del(keyPrefix + key);
        cache.invalidate(key);
    }

    final protected String getFromStore(String key) {
        String realKey = keyPrefix + key;
        String value = centralStore.get(realKey);
        if (value == null || value.isEmpty()) {
            // SOS
            centralStore.set("SOS:" + realKey, "", 10);
            log.warn("{} SOS:{}", LOG_TAG, realKey);
            value = null;
        }
        return value;
    }

    final protected T getFromStore(String key, Class<T> clazz) {
        String json = centralStore.get(keyPrefix + key);
        log.info("[PersistenceCache] before convert json:{}", json);
        if (json != null && !json.isEmpty())
            log.info("[PersistenceCache] after convert json:{}", JsonConverter.json2pojo(json, clazz));
        return (json == null || json.isEmpty()) ? null : JsonConverter.json2pojo(json, clazz);
    }
}
