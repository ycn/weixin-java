package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/23/15.
 */
public class WxRefreshTokenCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxRefreshTokenCache.class);
    private final static String LOG_TAG = "[WxRefreshTokenCache]";
    private static final AtomicReference<WxRefreshTokenCache> instance = new AtomicReference<WxRefreshTokenCache>();

    public static WxRefreshTokenCache init(WxTokenHandler wxTokenHandler,
                                           int refreshSeconds,
                                           int concurrencyLevel,
                                           long maximumSize,
                                           int executorSize) {

        WxRefreshTokenCache obj = instance.get();

        if (obj == null) {
            obj = new WxRefreshTokenCache(
                    wxTokenHandler,
                    refreshSeconds,
                    concurrencyLevel,
                    maximumSize,
                    executorSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxRefreshTokenCache getInstance() {
        return instance.get();
    }

    private WxRefreshTokenCache(WxTokenHandler wxTokenHandler,
                                int refreshSeconds,
                                int concurrencyLevel,
                                long maximumSize,
                                int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxRefreshTokenCacheLoader(executorSize),
                CacheKeyPrefix.REFRESH_TOKEN
        );
    }

    class WxRefreshTokenCacheLoader extends WxCacheLoader<String> {

        public WxRefreshTokenCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected String loadOne(String appId, String oldToken, boolean sync) {
            if (oldToken == null)
                oldToken = "";

            if (appId == null || appId.isEmpty())
                return oldToken;

            String token = getFromStore(appId);

            if (token == null || token.isEmpty()) {
                token = oldToken;
                log.error("{} (reload_cache) failed! appId:{}, current:{}, old:{}",
                        LOG_TAG, appId, token, oldToken);
            } else {
                log.info("{} (reload_cache) success! appId:{}, current:{}, old:{}",
                        LOG_TAG, appId, token, oldToken);
            }

            return token;
        }
    }
}
