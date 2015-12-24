package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.constant.CacheKeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/22/15.
 */
public class WxVerifyTicketCache extends PersistenceCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxVerifyTicketCache.class);
    private final static String LOG_TAG = "[WxVerifyTicketCache]";
    private static final AtomicReference<WxVerifyTicketCache> instance = new AtomicReference<WxVerifyTicketCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxVerifyTicketCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxVerifyTicketCache getInstance() {
        return instance.get();
    }

    private WxVerifyTicketCache(CentralStore centralStore,
                                int refreshSeconds,
                                int concurrencyLevel,
                                long maximumSize,
                                int executorSize) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxVerifyTicketCacheLoader(executorSize),
                CacheKeyPrefix.REFRESH_TOKEN
        );
    }

    class WxVerifyTicketCacheLoader extends WxCacheLoader<String> {

        public WxVerifyTicketCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected String loadOne(String appId, String oldTicket, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            if (oldTicket == null)
                oldTicket = "";

            String ticket = getFromStore(appId);
            return ticket == null ? oldTicket : ticket;
        }
    }
}
