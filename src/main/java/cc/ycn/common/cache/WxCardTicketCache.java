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
public class WxCardTicketCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxCardTicketCache.class);
    private final static String LOG_TAG = "[WxCardTicketCache]";
    private static final AtomicReference<WxCardTicketCache> instance = new AtomicReference<WxCardTicketCache>();

    public static WxCardTicketCache init(WxTokenHandler wxTokenHandler,
                                         int refreshSeconds,
                                         int concurrencyLevel,
                                         long maximumSize,
                                         int executorSize) {

        WxCardTicketCache obj = instance.get();

        if (obj == null) {
            obj = new WxCardTicketCache(
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

    public static WxCardTicketCache getInstance() {
        return instance.get();
    }

    private WxCardTicketCache(WxTokenHandler wxTokenHandler,
                              int refreshSeconds,
                              int concurrencyLevel,
                              long maximumSize,
                              int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxCardTicketCacheLoader(executorSize),
                WxCacheType.CARD_TICKET
        );
    }

    class WxCardTicketCacheLoader extends WxCacheLoader<String> {

        public WxCardTicketCacheLoader(int executorSize) {
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
