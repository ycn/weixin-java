package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andy on 12/22/15.
 */
public class WxVerifyTicketCache {
    public final static String KEY_PREFIX = "VerifyTicket:";
    private final static Logger log = LoggerFactory.getLogger(WxVerifyTicketCache.class);
    private final static String LOG_TAG = "[WxVerifyTicketCache]";
    private static final AtomicReference<WxVerifyTicketCache> instance = new AtomicReference<WxVerifyTicketCache>();
    private static final int REFRESH_SECONDS = 600;
    private static final int CONCURRENCY_LEVEL = 1;
    private static final long MAXIMUM_SIZE = 10;
    private static final int EXECUTOR_SIZE = 1;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxVerifyTicketCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxVerifyTicketCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxVerifyTicketCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxVerifyTicketCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, String> cache;

    private WxVerifyTicketCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxVerifyTicketCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxAccessTokenCacheLoader());
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

    class WxAccessTokenCacheLoader extends CacheLoader<String, String> {

        @Override
        public String load(String appId) throws Exception {
            return loadOne(appId, null, true);
        }

        @Override
        public ListenableFuture<String> reload(final String appId, final String oldTicket) throws Exception {
            checkNotNull(appId);
            checkNotNull(oldTicket);

            ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return loadOne(appId, oldTicket, false);
                }
            });

            executor.execute(task);
            return task;
        }

        private String loadOne(String appId, String oldTicket, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            if (oldTicket == null)
                oldTicket = "";

            String ticket = centralStore.get(KEY_PREFIX + appId);

            return ticket == null ? oldTicket : ticket;
        }
    }
}
