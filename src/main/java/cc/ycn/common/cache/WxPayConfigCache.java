package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxPayConfig;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 16/11/24.
 */
public class WxPayConfigCache extends ExpireCache<WxPayConfig> {
    private static final AtomicReference<WxPayConfigCache> instance = new AtomicReference<WxPayConfigCache>();

    public static WxPayConfigCache init(WxTokenHandler wxTokenHandler,
                                        int expireSeconds,
                                        int concurrencyLevel,
                                        long maximumSize) {

        WxPayConfigCache obj = instance.get();

        if (obj == null) {
            obj = new WxPayConfigCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxPayConfigCache getInstance() {
        return instance.get();
    }

    private WxPayConfigCache(WxTokenHandler wxTokenHandler,
                             int expireSeconds,
                             int concurrencyLevel,
                             long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.PAY_CONFIG
        );
    }
}
