package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.WxCacheType;
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

    public static WxAccessTokenCache init(WxTokenHandler wxTokenHandler,
                                          int refreshSeconds,
                                          int concurrencyLevel,
                                          long maximumSize,
                                          int executorSize) {

        WxAccessTokenCache obj = instance.get();

        if (obj == null) {
            obj = new WxAccessTokenCache(
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

    public static WxAccessTokenCache getInstance() {
        return instance.get();
    }

    private WxAccessTokenCache(WxTokenHandler wxTokenHandler,
                               int refreshSeconds,
                               int concurrencyLevel,
                               long maximumSize,
                               int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxAccessTokenCacheLoader(executorSize),
                WxCacheType.ACCESS_TOKEN
        );
    }

    class WxAccessTokenCacheLoader extends WxCacheLoader<String> {

        public WxAccessTokenCacheLoader(int executorSize) {
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
