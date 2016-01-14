package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/11/15.
 */
public class WxConfigCache extends PersistenceCache<WxConfig> {
    private final static Logger log = LoggerFactory.getLogger(WxConfigCache.class);
    private final static String LOG_TAG = "[WxConfigCache]";
    private static final AtomicReference<WxConfigCache> instance = new AtomicReference<WxConfigCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxConfigCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxConfigCache getInstance() {
        return instance.get();
    }

    private WxConfigCache(CentralStore centralStore,
                          int refreshSeconds,
                          int concurrencyLevel,
                          long maximumSize,
                          int executorSize,
                          boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxConfigCacheLoader(executorSize, readonly),
                CacheKeyPrefix.CONFIG,
                readonly
        );
    }

    class WxConfigCacheLoader extends WxCacheLoader<WxConfig> {

        public WxConfigCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected WxConfig loadOneReadonly(String appId, WxConfig oldConfig, boolean sync) {
            if (oldConfig == null)
                oldConfig = new WxConfig();

            if (appId == null || appId.isEmpty())
                return oldConfig;

            WxConfig config = getFromStore(appId, WxConfig.class);
            log.info("{} reload success! (readonly) appId:{}, use newConfig", LOG_TAG, appId);
            return config == null ? oldConfig : config;
        }

        @Override
        protected WxConfig loadOne(String appId, WxConfig oldConfig, boolean sync) {
            if (oldConfig == null)
                oldConfig = new WxConfig();

            if (appId == null || appId.isEmpty())
                return oldConfig;
            
            WxConfig config = getFromStore(appId, WxConfig.class);
            log.info("{} reload success! appId:{}, use newConfig", LOG_TAG, appId);
            return config == null ? oldConfig : config;
        }
    }
}
