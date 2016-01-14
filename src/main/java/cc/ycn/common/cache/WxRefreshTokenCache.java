package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/23/15.
 */
public class WxRefreshTokenCache extends PersistenceCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxRefreshTokenCache.class);
    private final static String LOG_TAG = "[WxRefreshTokenCache]";
    private static final AtomicReference<WxRefreshTokenCache> instance = new AtomicReference<WxRefreshTokenCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxRefreshTokenCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxRefreshTokenCache getInstance() {
        return instance.get();
    }

    private WxRefreshTokenCache(CentralStore centralStore,
                                int refreshSeconds,
                                int concurrencyLevel,
                                long maximumSize,
                                int executorSize,
                                boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxRefreshTokenCacheLoader(executorSize, readonly),
                CacheKeyPrefix.REFRESH_TOKEN,
                readonly
        );
    }

    class WxRefreshTokenCacheLoader extends WxCacheLoader<String> {

        public WxRefreshTokenCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected String loadOneReadonly(String appId, String oldToken, boolean sync) {
            if (oldToken == null)
                oldToken = "";

            if (appId == null || appId.isEmpty())
                return oldToken;

            String token = getFromStore(appId);
            log.info("{} reload success! (readonly) appId:{}, use newRefreshToken:{}, oldRefreshToken:{}", LOG_TAG, appId, token, oldToken);
            return token == null ? oldToken : token;
        }

        @Override
        protected String loadOne(String appId, String oldToken, boolean sync) {
            if (oldToken == null)
                oldToken = "";

            if (appId == null || appId.isEmpty())
                return oldToken;

            String token = getFromStore(appId);
            log.info("{} reload success! appId:{}, use newRefreshToken:{}, oldRefreshToken:{}", LOG_TAG, appId, token, oldToken);
            return token == null ? oldToken : token;
        }
    }
}
