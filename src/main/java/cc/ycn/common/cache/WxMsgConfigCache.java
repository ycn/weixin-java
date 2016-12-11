package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.constant.WxCacheType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/11/15.
 */
public class WxMsgConfigCache extends ExpireCache<WxMsgConfig> {
    private static final AtomicReference<WxMsgConfigCache> instance = new AtomicReference<WxMsgConfigCache>();

    public static WxMsgConfigCache init(WxTokenHandler wxTokenHandler,
                                        int expireSeconds,
                                        int concurrencyLevel,
                                        long maximumSize) {

        WxMsgConfigCache obj = instance.get();

        if (obj == null) {
            obj = new WxMsgConfigCache(
                    wxTokenHandler,
                    expireSeconds,
                    concurrencyLevel,
                    maximumSize
            );
            instance.compareAndSet(null, obj);
        }

        return obj;
    }

    public static WxMsgConfigCache getInstance() {
        return instance.get();
    }

    private WxMsgConfigCache(WxTokenHandler wxTokenHandler,
                             int expireSeconds,
                             int concurrencyLevel,
                             long maximumSize) {
        init(wxTokenHandler,
                expireSeconds,
                concurrencyLevel,
                maximumSize,
                WxCacheType.MSG_CONFIG
        );
    }
}
