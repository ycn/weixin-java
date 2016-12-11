package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/24/15.
 */
public class WxPreAuthCodeCache extends ExpireCache<String> {
    private static final AtomicReference<WxPreAuthCodeCache> instance = new AtomicReference<WxPreAuthCodeCache>();

    public static WxPreAuthCodeCache init(WxTokenHandler wxTokenHandler,
                                          int expireSeconds,
                                          int concurrencyLevel,
                                          long maximumSize) {

        WxPreAuthCodeCache obj = instance.get();

        if (obj == null) {
            obj = new WxPreAuthCodeCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxPreAuthCodeCache getInstance() {
        return instance.get();
    }

    private WxPreAuthCodeCache(WxTokenHandler wxTokenHandler,
                               int expireSeconds,
                               int concurrencyLevel,
                               long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.PRE_AUTHCODE
        );
    }
}
