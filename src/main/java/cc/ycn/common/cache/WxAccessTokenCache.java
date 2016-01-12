package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.component.WxComponentServiceImpl;
import cc.ycn.component.bean.WxAuthorizerAccessToken;
import cc.ycn.cp.WxCpServiceImpl;
import cc.ycn.mp.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/11/15.
 */
public class WxAccessTokenCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxAccessTokenCache.class);
    private final static String LOG_TAG = "[WxAccessTokenCache]";
    private static final AtomicReference<WxAccessTokenCache> instance = new AtomicReference<WxAccessTokenCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxAccessTokenCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxAccessTokenCache getInstance() {
        return instance.get();
    }

    private WxAccessTokenCache(CentralStore centralStore,
                               int refreshSeconds,
                               int concurrencyLevel,
                               long maximumSize,
                               int executorSize,
                               boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxAccessTokenCacheLoader(executorSize, readonly),
                CacheKeyPrefix.ACCESS_TOKEN,
                readonly
        );
    }

    class WxAccessTokenCacheLoader extends WxCacheLoader<String> {

        public WxAccessTokenCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected String loadOneReadonly(String appId, String oldToken, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            String token = getFromStore(appId);
            log.info("{} reload success! (readonly) appId:{}, use newAccessToken:{}, oldAccessToken:{}", LOG_TAG, appId, token, oldToken);
            return token == null ? oldToken : token;
        }

        @Override
        protected String loadOne(String appId, String oldToken, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null) {
                log.warn("{} missing config. appId:{}, use oldAccessToken:{}", LOG_TAG, appId, oldToken);
                return oldToken;
            }

            // 检查refreshToken
            WxRefreshTokenCache refreshTokenCache = WxRefreshTokenCache.getInstance();
            if (config.isAuthorizer()) {
                String refreshToken = refreshTokenCache.get(appId);
                if (refreshToken == null || refreshToken.isEmpty()) {
                    log.warn("{} missing refreshToken. appId:{}, use oldAccessToken:{}", LOG_TAG, appId, oldToken);
                    return oldToken;
                }
            }

            // accessToken还未过期
            String token = getFromStore(appId);

            if (token != null && !token.isEmpty()) {
                // 有效继续使用
                log.info("{} appId:{}, reuse oldAccessToken:{}", LOG_TAG, appId, token);
                return token;
            }

            // accessToken已过期
            WxAccessToken accessToken = null;

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
                            refreshTokenCache.setToStore(appId, authorizerAccessToken.getAuthorizerRefreshToken());
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
                log.warn("{} request error:{}, appId:{}, use oldAccessToken:{}", LOG_TAG, e.getError(), appId, oldToken);
                return oldToken;
            }

            if (accessToken == null || accessToken.getAccessToken() == null || accessToken.getAccessToken().isEmpty()) {
                log.warn("{} empty resp, appId:{}, use oldAccessToken:{}", LOG_TAG, appId, oldToken);
                return oldToken;
            }

            token = accessToken.getAccessToken();

            setToStore(appId, token, accessToken.getExpiresIn());

            log.info("{} reload success! appId:{}, use newAccessToken:{}, oldAccessToken:{}", LOG_TAG, appId, token, oldToken);
            return token;
        }
    }
}
