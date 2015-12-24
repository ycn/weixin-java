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
                            int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxConfigCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxConfigCache getInstance() {
        return instance.get();
    }

    private WxConfigCache(CentralStore centralStore,
                          int refreshSeconds,
                          int concurrencyLevel,
                          long maximumSize,
                          int executorSize) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxConfigCacheLoader(executorSize),
                CacheKeyPrefix.CONFIG
        );
    }

    class WxConfigCacheLoader extends WxCacheLoader<WxConfig> {

        public WxConfigCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected WxConfig loadOne(String appId, WxConfig oldConfig, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            WxConfig config = getFromStore(appId, WxConfig.class);

            return config == null ? oldConfig : config;
        }
    }
}
