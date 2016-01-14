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
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxVerifyTicketCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxVerifyTicketCache getInstance() {
        return instance.get();
    }

    private WxVerifyTicketCache(CentralStore centralStore,
                                int refreshSeconds,
                                int concurrencyLevel,
                                long maximumSize,
                                int executorSize,
                                boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxVerifyTicketCacheLoader(executorSize, readonly),
                CacheKeyPrefix.VERIFY_TICKET,
                readonly
        );
    }

    class WxVerifyTicketCacheLoader extends WxCacheLoader<String> {

        public WxVerifyTicketCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected String loadOneReadonly(String appId, String oldTicket, boolean sync) {
            if (oldTicket == null)
                oldTicket = "";

            if (appId == null || appId.isEmpty())
                return oldTicket;

            String ticket = getFromStore(appId);
            log.info("{} reload success! (readonly) appId:{}, use newVerifyTicket:{}, oldVerifyTicket:{}", LOG_TAG, appId, ticket, oldTicket);
            return ticket == null ? oldTicket : ticket;
        }

        @Override
        protected String loadOne(String appId, String oldTicket, boolean sync) {
            if (oldTicket == null)
                oldTicket = "";

            if (appId == null || appId.isEmpty())
                return oldTicket;

            String ticket = getFromStore(appId);
            log.info("{} reload success! appId:{}, use newVerifyTicket:{}, oldVerifyTicket:{}", LOG_TAG, appId, ticket, oldTicket);
            return ticket == null ? oldTicket : ticket;
        }
    }
}
