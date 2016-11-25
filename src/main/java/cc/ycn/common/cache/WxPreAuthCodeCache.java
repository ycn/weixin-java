package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/24/15.
 */
public class WxPreAuthCodeCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxPreAuthCodeCache.class);
    private final static String LOG_TAG = "[WxPreAuthCodeCache]";
    private static final AtomicReference<WxPreAuthCodeCache> instance = new AtomicReference<WxPreAuthCodeCache>();

    public static WxPreAuthCodeCache init(WxTokenHandler wxTokenHandler,
                                          int refreshSeconds,
                                          int concurrencyLevel,
                                          long maximumSize,
                                          int executorSize) {

        WxPreAuthCodeCache obj = instance.get();

        if (obj == null) {
            obj = new WxPreAuthCodeCache(
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

    public static WxPreAuthCodeCache getInstance() {
        return instance.get();
    }

    private WxPreAuthCodeCache(WxTokenHandler wxTokenHandler,
                               int refreshSeconds,
                               int concurrencyLevel,
                               long maximumSize,
                               int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxPreAuthCodeCacheLoader(executorSize),
                CacheKeyPrefix.PRE_AUTHCODE
        );
    }

    class WxPreAuthCodeCacheLoader extends WxCacheLoader<String> {

        public WxPreAuthCodeCacheLoader(int executorSize) {
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
