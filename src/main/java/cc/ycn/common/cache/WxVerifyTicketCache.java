package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/22/15.
 */
public class WxVerifyTicketCache extends ExpireCache<String> {
    private static final AtomicReference<WxVerifyTicketCache> instance = new AtomicReference<WxVerifyTicketCache>();

    public static WxVerifyTicketCache init(WxTokenHandler wxTokenHandler,
                                           int expireSeconds,
                                           int concurrencyLevel,
                                           long maximumSize) {

        WxVerifyTicketCache obj = instance.get();

        if (obj == null) {
            obj = new WxVerifyTicketCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxVerifyTicketCache getInstance() {
        return instance.get();
    }

    private WxVerifyTicketCache(WxTokenHandler wxTokenHandler,
                                int expireSeconds,
                                int concurrencyLevel,
                                long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.VERIFY_TICKET
        );
    }
}
