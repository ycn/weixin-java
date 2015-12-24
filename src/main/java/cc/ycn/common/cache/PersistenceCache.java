package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.util.JsonConverter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * Created by andy on 12/24/15.
 */
public abstract class PersistenceCache<T> {

    private CentralStore centralStore;
    private LoadingCache<String, T> cache;
    private String keyPrefix;

    protected PersistenceCache() {

    }

    protected void init(CentralStore centralStore,
                        int refreshSeconds,
                        int concurrencyLevel,
                        long maximumCacheSize,
                        WxCacheLoader<T> cacheLoader,
                        CacheKeyPrefix keyPrefix) {

        this.centralStore = centralStore;

        // reload after expired
        this.cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumCacheSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(cacheLoader);

        this.keyPrefix = keyPrefix.prefix();
    }

    final public T get(String key) {
        return cache.getUnchecked(key);
    }

    final public void invalidate(String key) {
        cache.invalidate(key);
    }

    final public T refresh(String key) {
        invalidate(key);
        return get(key);
    }

    final public void setToStore(String key, T value) {
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
        centralStore.del(keyPrefix + key);
        cache.invalidate(key);
    }

    final protected String getFromStore(String key) {
        return centralStore.get(keyPrefix + key);
    }

    final protected T getFromStore(String key, Class<T> clazz) {
        String json = centralStore.get(keyPrefix + key);
        return (json == null || json.isEmpty()) ? null : JsonConverter.json2pojo(json, clazz);
    }
}
