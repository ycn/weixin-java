package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.WxCacheType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/11/15.
 */
public class WxMsgConfigCache extends ExpireCache<WxMsgConfig> {
    private final static Logger log = LoggerFactory.getLogger(WxMsgConfigCache.class);
    private final static String LOG_TAG = "[WxMsgConfigCache]";
    private static final AtomicReference<WxMsgConfigCache> instance = new AtomicReference<WxMsgConfigCache>();

    public static WxMsgConfigCache init(WxTokenHandler wxTokenHandler,
                                        int refreshSeconds,
                                        int concurrencyLevel,
                                        long maximumSize,
                                        int executorSize) {

        WxMsgConfigCache obj = instance.get();

        if (obj == null) {
            obj = new WxMsgConfigCache(
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

    public static WxMsgConfigCache getInstance() {
        return instance.get();
    }

    private WxMsgConfigCache(WxTokenHandler wxTokenHandler,
                             int refreshSeconds,
                             int concurrencyLevel,
                             long maximumSize,
                             int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxMsgConfigCacheLoader(executorSize),
                WxCacheType.MSG_CONFIG
        );
    }

    class WxMsgConfigCacheLoader extends WxCacheLoader<WxMsgConfig> {

        public WxMsgConfigCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected WxMsgConfig loadOne(String appId, WxMsgConfig oldConfig, boolean sync) {
            if (oldConfig == null)
                oldConfig = new WxMsgConfig();

            if (appId == null || appId.isEmpty())
                return oldConfig;

            WxMsgConfig config = getFromStore(appId, WxMsgConfig.class);

            if (config == null) {
                config = oldConfig;
                log.error("{} (reload_cache) failed! appId:{}",
                        LOG_TAG, appId);
            } else {
                log.info("{} (reload_cache) success! appId:{}",
                        LOG_TAG, appId);
            }

            return config;
        }
    }
}
