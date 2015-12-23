package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxCardTicket;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.JsonConverter;
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
public class WxCardTicketCache {
    public final static String KEY_PREFIX = "CardTicket:";
    private final static Logger log = LoggerFactory.getLogger(WxCardTicketCache.class);
    private final static String LOG_TAG = "[WxCardTicketCache]";
    private static final AtomicReference<WxCardTicketCache> instance = new AtomicReference<WxCardTicketCache>();
    private static final int REFRESH_SECONDS = 7200;
    private static final int CONCURRENCY_LEVEL = 10;
    private static final long MAXIMUM_SIZE = 10000;
    private static final int EXECUTOR_SIZE = 10;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCardTicketCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCardTicketCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCardTicketCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxCardTicketCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, String> cache;

    private WxCardTicketCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxCardTicketCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxCardTicketCacheLoader());
    }

    public String getTicket(String appId) {
        return cache.getUnchecked(appId);
    }

    public void invalidate(String appId) {
        cache.invalidate(KEY_PREFIX + appId);
    }

    class WxCardTicketCacheLoader extends CacheLoader<String, String> {

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
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.getConfig(appId);
            if (config == null)
                return oldTicket;

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.getToken(appId);
            if (accessToken == null)
                return oldTicket;

            // ticket还未过期
            String ticketJson = centralStore.get(KEY_PREFIX + appId);
            WxCardTicket ticket = ticketJson == null ? null : JsonConverter.json2pojo(ticketJson, WxCardTicket.class);
            if (ticket != null && ticket.getTicket() != null && !ticket.getTicket().isEmpty()) {
                return ticket.getTicket();
            }

            // ticket已过期
            try {

                switch (config.getType()) {
                    case MP:
                        WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                        ticket = wxMpService.fetchCardTicket();
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
