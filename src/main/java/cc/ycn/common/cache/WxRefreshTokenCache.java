package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/23/15.
 */
public class WxRefreshTokenCache extends ExpireCache<String> {
    private static final AtomicReference<WxRefreshTokenCache> instance = new AtomicReference<WxRefreshTokenCache>();

    public static WxRefreshTokenCache init(WxTokenHandler wxTokenHandler,
                                           int expireSeconds,
                                           int concurrencyLevel,
                                           long maximumSize) {

        WxRefreshTokenCache obj = instance.get();

        if (obj == null) {
            obj = new WxRefreshTokenCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxRefreshTokenCache getInstance() {
        return instance.get();
    }

    private WxRefreshTokenCache(WxTokenHandler wxTokenHandler,
                                int expireSeconds,
                                int concurrencyLevel,
                                long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.REFRESH_TOKEN
        );
    }
}
