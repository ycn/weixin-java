package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andy on 12/23/15.
 */
public class WxRefreshTokenCache {
    public final static String KEY_PREFIX = "AuthorizerRefreshToken:";
    private static final AtomicReference<WxRefreshTokenCache> instance = new AtomicReference<WxRefreshTokenCache>();
    private static final int REFRESH_SECONDS = 86400;
    private static final int CONCURRENCY_LEVEL = 10;
    private static final long MAXIMUM_SIZE = 10000;
    private static final int EXECUTOR_SIZE = 10;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxRefreshTokenCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxRefreshTokenCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxRefreshTokenCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxRefreshTokenCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, String> cache;

    private WxRefreshTokenCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxRefreshTokenCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxAuthorizerRefreshTokenCacheLoader());
    }

    public String get(String appId) {
        return cache.getUnchecked(appId);
    }

    public void set(String appId, String value) {
        if (appId == null || appId.isEmpty())
            return;
        if (value == null || value.isEmpty())
            return;
        centralStore.set(KEY_PREFIX + appId, value);
        cache.invalidate(appId);
    }

    public void del(String appId) {
        centralStore.del(KEY_PREFIX + appId);
        cache.invalidate(appId);
    }

    public void invalidate(String appId) {
        cache.invalidate(appId);
    }


    class WxAuthorizerRefreshTokenCacheLoader extends CacheLoader<String, String> {

        @Override
        public String load(String appId) throws Exception {
            return loadOne(appId, null, true);
        }

        @Override
        public ListenableFuture<String> reload(final String appId, final String oldToken) throws Exception {
            checkNotNull(appId);
            checkNotNull(oldToken);

            ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return loadOne(appId, oldToken, false);
                }
            });

            executor.execute(task);
            return task;
        }

        private String loadOne(String appId, String oldToken, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            String tokenStr = centralStore.get(KEY_PREFIX + appId);
            return tokenStr == null ? oldToken : tokenStr;
        }
    }
}
