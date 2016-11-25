package cc.ycn.common.cache;

import cc.ycn.common.api.WxTokenHandler;
import cc.ycn.common.bean.WxPayConfig;
import cc.ycn.common.cache.base.ExpireCache;
import cc.ycn.common.cache.base.WxCacheLoader;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 16/11/24.
 */
public class WxPayConfigCache extends ExpireCache<WxPayConfig> {
    private final static Logger log = LoggerFactory.getLogger(WxPayConfigCache.class);
    private final static String LOG_TAG = "[WxPayConfigCache]";
    private static final AtomicReference<WxPayConfigCache> instance = new AtomicReference<WxPayConfigCache>();

    public static WxPayConfigCache init(WxTokenHandler wxTokenHandler,
                                        int refreshSeconds,
                                        int concurrencyLevel,
                                        long maximumSize,
                                        int executorSize) {

        WxPayConfigCache obj = instance.get();

        if (obj == null) {
            obj = new WxPayConfigCache(
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

    public static WxPayConfigCache getInstance() {
        return instance.get();
    }

    private WxPayConfigCache(WxTokenHandler wxTokenHandler,
                             int refreshSeconds,
                             int concurrencyLevel,
                             long maximumSize,
                             int executorSize) {
        init(wxTokenHandler,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxPayConfigCacheLoader(executorSize),
                CacheKeyPrefix.PAY_CONFIG
        );
    }

    class WxPayConfigCacheLoader extends WxCacheLoader<WxPayConfig> {

        public WxPayConfigCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected WxPayConfig loadOne(String appId, WxPayConfig oldConfig, boolean sync) {
            if (oldConfig == null)
                oldConfig = new WxPayConfig();

            if (appId == null || appId.isEmpty())
                return oldConfig;

            WxPayConfig config = getFromStore(appId, WxPayConfig.class);

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
