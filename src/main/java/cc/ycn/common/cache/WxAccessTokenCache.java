package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.JsonConverter;
import cc.ycn.component.WxComponentServiceImpl;
import cc.ycn.component.bean.WxAuthorizerAccessToken;
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
 * Created by andy on 12/11/15.
 */
public class WxAccessTokenCache {
    public final static String KEY_PREFIX = "AccessToken:";
    private final static Logger log = LoggerFactory.getLogger(WxAccessTokenCache.class);
    private final static String LOG_TAG = "[WxAccessTokenCache]";
    private static final AtomicReference<WxAccessTokenCache> instance = new AtomicReference<WxAccessTokenCache>();
    private static final int REFRESH_SECONDS = 7200;
    private static final int CONCURRENCY_LEVEL = 10;
    private static final long MAXIMUM_SIZE = 10000;
    private static final int EXECUTOR_SIZE = 10;
    private static ExecutorService executor;

    public static void init(CentralStore centralStore) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxAccessTokenCache(centralStore, REFRESH_SECONDS));
    }

    public static void init(CentralStore centralStore, int refreshSeconds) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxAccessTokenCache(centralStore, refreshSeconds));
    }

    public static void init(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxAccessTokenCache(centralStore, refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxAccessTokenCache getInstance() {
        return instance.get();
    }

    private CentralStore centralStore;
    private LoadingCache<String, String> cache;

    private WxAccessTokenCache(CentralStore centralStore, int refreshSeconds) {
        this(centralStore, refreshSeconds, CONCURRENCY_LEVEL, MAXIMUM_SIZE, EXECUTOR_SIZE);
    }

    private WxAccessTokenCache(CentralStore centralStore, int refreshSeconds, int concurrencyLevel, long maximumSize, int executorSize) {
        executor = Executors.newFixedThreadPool(executorSize);

        this.centralStore = centralStore;

        // reload after expired
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                .build(new WxAccessTokenCacheLoader());
    }

    public String getToken(String appId) {
        return cache.getUnchecked(appId);
    }

    public void setToken(String appId, String token, long expiredIn) {
        if (appId == null || appId.isEmpty())
            return;
        if (token == null || token.isEmpty())
            return;
        centralStore.set(KEY_PREFIX + appId, token, expiredIn);
        cache.invalidate(appId);
    }

    public void invalidate(String appId) {
        cache.invalidate(KEY_PREFIX + appId);
    }

    class WxAccessTokenCacheLoader extends CacheLoader<String, String> {

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
                return "";

            if (oldToken == null)
                oldToken = "";

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.getConfig(appId);
            if (config == null) {
                log.warn("{} missing config, appId:{}, use oldToken:{}", LOG_TAG, appId, oldToken);
                return oldToken;
            }

            // 检查refreshToken
            WxRefreshTokenCache refreshTokenCache = WxRefreshTokenCache.getInstance();
            if (config.isAuthorizer()) {
                String refreshToken = refreshTokenCache.getToken(appId);
                if (refreshToken == null || refreshToken.isEmpty()) {
                    log.warn("{} missing refreshToken, appId:{}, use oldToken:{}", LOG_TAG, appId, oldToken);
                    return oldToken;
                }
            }

            // accessToken还未过期
            String tokenJson = centralStore.get(KEY_PREFIX + appId);
            WxAccessToken accessToken = tokenJson == null ? null : JsonConverter.json2pojo(tokenJson, WxAccessToken.class);
            if (accessToken != null && accessToken.getAccessToken() != null && !accessToken.getAccessToken().isEmpty()) {

                // 测试accessToken是否有效
                try {

                    switch (config.getType()) {
                        case MP:
                            if (!config.isAuthorizer()) {
                                WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                                wxMpService.verifyAccessToken(accessToken.getAccessToken());
                            }
                            break;
                        case CP:
                            WxCpServiceImpl wxCpService = new WxCpServiceImpl(appId);
                            wxCpService.verifyAccessToken(accessToken.getAccessToken());
                            break;
                        case COMPONENT:
                            WxComponentServiceImpl wxComponentService = new WxComponentServiceImpl(appId);
                            wxComponentService.verifyAccessToken(accessToken.getAccessToken());
                            break;
                        default:
                            break;
                    }

                    // 有效继续使用
                    log.info("{} use oldToken: {}", LOG_TAG, accessToken);
                    return accessToken.getAccessToken();

                } catch (WxErrorException e) {
                    log.warn("{} token invalid: {}, {}", LOG_TAG, accessToken, e.getError());
                }
            }

            // accessToken已过期
            try {

                switch (config.getType()) {
                    case MP:
                        if (!config.isAuthorizer()) {
                            WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                            accessToken = wxMpService.fetchAccessToken();
                        } else {
                            WxComponentServiceImpl wxComponentService = new WxComponentServiceImpl(config.getComponentAppId());
                            WxAuthorizerAccessToken authorizerAccessToken = wxComponentService.refreshAuthorizerAccessToken(appId);
                            // 更新refreshToken
                            refreshTokenCache.setToken(appId, authorizerAccessToken.getAuthorizerRefreshToken());
                            accessToken = new WxAccessToken(authorizerAccessToken);
                        }
                        break;
                    case CP:
                        WxCpServiceImpl wxCpService = new WxCpServiceImpl(appId);
                        accessToken = wxCpService.fetchAccessToken();
                        break;
                    case COMPONENT:
                        WxComponentServiceImpl wxComponentService = new WxComponentServiceImpl(appId);
                        accessToken = new WxAccessToken(wxComponentService.fetchAccessToken());
                        break;
                    default:
                        break;
                }

            } catch (WxErrorException e) {
                log.warn("{} request error: {}", LOG_TAG, e.getError());
                return oldToken;
            }

            if (accessToken == null || accessToken.getAccessToken() == null || accessToken.getAccessToken().isEmpty())
                return oldToken;

            centralStore.set(KEY_PREFIX + appId, JsonConverter.pojo2json(accessToken), accessToken.getExpiresIn());

            return accessToken.getAccessToken();
        }
    }
}
