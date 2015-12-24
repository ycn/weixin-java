package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.util.JsonConverter;
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
 * Created by andy on 12/11/15.
 */
public class WxConfigCache {
    public final static String KEY_PREFIX = "";
    private static final AtomicReference<WxConfigCache> instance = new AtomicReference<WxConfigCache>();
    private static final int REFRESH_SECONDS = 86400;
    private static final int CONCURRENCY_LEVEL = 10;
    private static final long MAXIMUM_SIZE = 10000;
    private static final int EXECUTOR_SIZE = 10;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxConfigCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxConfigCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxConfigCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxConfigCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, WxConfig> cache;

    private WxConfigCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxConfigCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxConfigCacheLoader());
    }

    public WxConfig get(String appId) {
        return cache.getUnchecked(appId);
    }

    public void set(String appId, WxConfig value) {
        if (appId == null || appId.isEmpty())
            return;
        if (value == null)
            return;
        centralStore.set(KEY_PREFIX + appId, JsonConverter.pojo2json(value));
        cache.invalidate(appId);
    }

    public void del(String appId) {
        centralStore.del(KEY_PREFIX + appId);
        cache.invalidate(appId);
    }

    public void invalidate(String appId) {
        cache.invalidate(appId);
    }

    class WxConfigCacheLoader extends CacheLoader<String, WxConfig> {

        @Override
        public WxConfig load(String appId) throws Exception {
            return loadOne(appId, null, true);
        }

        @Override
        public ListenableFuture<WxConfig> reload(final String appId, final WxConfig oldConfig) throws Exception {
            checkNotNull(appId);
            checkNotNull(oldConfig);

            ListenableFutureTask<WxConfig> task = ListenableFutureTask.create(new Callable<WxConfig>() {
                @Override
                public WxConfig call() throws Exception {
                    return loadOne(appId, oldConfig, false);
                }
            });

            executor.execute(task);
            return task;
        }

        private WxConfig loadOne(String appId, WxConfig oldConfig, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            String configJson = centralStore.get(KEY_PREFIX + appId);
            WxConfig config = configJson == null ? null : JsonConverter.json2pojo(configJson, WxConfig.class);
            return config == null ? oldConfig : config;
        }
    }
}
