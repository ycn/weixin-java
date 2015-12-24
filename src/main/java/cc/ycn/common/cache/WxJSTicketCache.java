package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxJSTicket;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.JsonConverter;
import cc.ycn.cp.WxCpServiceImpl;
import cc.ycn.mp.WxMpServiceImpl;
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
 * Created by andy on 12/17/15.
 */
public class WxJSTicketCache {
    public final static String KEY_PREFIX = "JSTicket:";
    private final static Logger log = LoggerFactory.getLogger(WxJSTicketCache.class);
    private final static String LOG_TAG = "[WxJSTicketCache]";
    private static final AtomicReference<WxJSTicketCache> instance = new AtomicReference<WxJSTicketCache>();
    private static final int REFRESH_SECONDS = 7200;
    private static final int CONCURRENCY_LEVEL = 10;
    private static final long MAXIMUM_SIZE = 10000;
    private static final int EXECUTOR_SIZE = 10;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxJSTicketCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxJSTicketCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxJSTicketCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxJSTicketCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, String> cache;

    private WxJSTicketCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxJSTicketCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxJSTicketCacheLoader());
    }

    public String get(String appId) {
        return cache.getUnchecked(appId);
    }

    public void set(String appId, String value, long expiredIn) {
        if (appId == null || appId.isEmpty())
            return;
        if (value == null || value.isEmpty())
            return;
        centralStore.set(KEY_PREFIX + appId, value, expiredIn);
        cache.invalidate(appId);
    }

    public void del(String appId) {
        centralStore.del(KEY_PREFIX + appId);
        cache.invalidate(appId);
    }

    public void invalidate(String appId) {
        cache.invalidate(appId);
    }

    class WxJSTicketCacheLoader extends CacheLoader<String, String> {

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
                return "";

            if (oldTicket == null)
                oldTicket = "";

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null)
                return oldTicket;

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.get(appId);
            if (accessToken == null)
                return oldTicket;

            // ticket还未过期
            String ticketJson = centralStore.get(KEY_PREFIX + appId);
            WxJSTicket ticket = ticketJson == null ? null : JsonConverter.json2pojo(ticketJson, WxJSTicket.class);
            if (ticket != null && ticket.getTicket() != null && !ticket.getTicket().isEmpty()) {
                return ticket.getTicket();
            }

            // ticket已过期
            try {

                switch (config.getType()) {
                    case MP:
                        WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                        ticket = wxMpService.fetchJSTicket();
                        break;
                    case CP:
                        WxCpServiceImpl wxCpService = new WxCpServiceImpl(appId);
                        ticket = wxCpService.fetchJSTicket();
                        break;
                    default:
                        break;
                }

            } catch (WxErrorException e) {
                log.warn("{} request error: {}", LOG_TAG, e.getError());
                return oldTicket;
            }

            if (ticket == null || ticket.getTicket() == null || ticket.getTicket().isEmpty())
                return oldTicket;

            centralStore.set(KEY_PREFIX + appId, JsonConverter.pojo2json(ticket), ticket.getExpiresIn());

            return ticket.getTicket();
        }
    }
}
