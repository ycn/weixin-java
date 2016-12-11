package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/17/15.
 */
public class WxCardTicketCache extends ExpireCache<String> {
    private static final AtomicReference<WxCardTicketCache> instance = new AtomicReference<WxCardTicketCache>();

    public static WxCardTicketCache init(WxTokenHandler wxTokenHandler,
                                         int expireSeconds,
                                         int concurrencyLevel,
                                         long maximumSize) {

        WxCardTicketCache obj = instance.get();

        if (obj == null) {
            obj = new WxCardTicketCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxCardTicketCache getInstance() {
        return instance.get();
    }

    private WxCardTicketCache(WxTokenHandler wxTokenHandler,
                              int expireSeconds,
                              int concurrencyLevel,
                              long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.CARD_TICKET
        );
    }
}
