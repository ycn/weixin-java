package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/11/15.
 */
public class WxAccessTokenCache extends ExpireCache<String> {
    private static final AtomicReference<WxAccessTokenCache> instance = new AtomicReference<WxAccessTokenCache>();

    public static WxAccessTokenCache init(WxTokenHandler wxTokenHandler,
                                          int expireSeconds,
                                          int concurrencyLevel,
                                          long maximumSize) {

        WxAccessTokenCache obj = instance.get();

        if (obj == null) {
            obj = new WxAccessTokenCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxAccessTokenCache getInstance() {
        return instance.get();
    }

    private WxAccessTokenCache(WxTokenHandler wxTokenHandler,
                               int expireSeconds,
                               int concurrencyLevel,
                               long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.ACCESS_TOKEN
        );
    }
}
