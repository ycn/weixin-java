package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/17/15.
 */
public class WxJSTicketCache extends ExpireCache<String> {
    private static final AtomicReference<WxJSTicketCache> instance = new AtomicReference<WxJSTicketCache>();

    public static WxJSTicketCache init(WxTokenHandler wxTokenHandler,
                                       int expireSeconds,
                                       int concurrencyLevel,
                                       long maximumSize) {

        WxJSTicketCache obj = instance.get();

        if (obj == null) {
            obj = new WxJSTicketCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxJSTicketCache getInstance() {
        return instance.get();
    }

    private WxJSTicketCache(WxTokenHandler wxTokenHandler,
                            int expireSeconds,
                            int concurrencyLevel,
                            long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.JS_TICKET
        );
    }
}
