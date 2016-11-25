package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.WxCacheType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/17/15.
 */
public class WxJSTicketCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxJSTicketCache.class);
    private final static String LOG_TAG = "[WxJSTicketCache]";
    private static final AtomicReference<WxJSTicketCache> instance = new AtomicReference<WxJSTicketCache>();

    public static WxJSTicketCache init(WxTokenHandler wxTokenHandler,
                                       int refreshSeconds,
                                       int concurrencyLevel,
                                       long maximumSize,
                                       int executorSize) {

        WxJSTicketCache obj = instance.get();

        if (obj == null) {
            obj = new WxJSTicketCache(
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

    public static WxJSTicketCache getInstance() {
        return instance.get();
    }

    private WxJSTicketCache(WxTokenHandler wxTokenHandler,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxJSTicketCacheLoader(executorSize),
                WxCacheType.JS_TICKET
        );
    }

    class WxJSTicketCacheLoader extends WxCacheLoader<String> {

        public WxJSTicketCacheLoader(int executorSize) {
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
