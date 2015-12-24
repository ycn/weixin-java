package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxCardTicket;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.mp.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/17/15.
 */
public class WxCardTicketCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxCardTicketCache.class);
    private final static String LOG_TAG = "[WxCardTicketCache]";
    private static final AtomicReference<WxCardTicketCache> instance = new AtomicReference<WxCardTicketCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCardTicketCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxCardTicketCache getInstance() {
        return instance.get();
    }

    private WxCardTicketCache(CentralStore centralStore,
                              int refreshSeconds,
                              int concurrencyLevel,
                              long maximumSize,
                              int executorSize) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxCardTicketCacheLoader(executorSize),
                CacheKeyPrefix.CARD_TICKET
        );
    }

    class WxCardTicketCacheLoader extends WxCacheLoader<String> {

        public WxCardTicketCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected String loadOne(String appId, String oldTicket, boolean sync) {
            if (appId == null || appId.isEmpty())
                return "";

            if (oldTicket == null)
                oldTicket = "";

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null)
                return oldTicket;

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.get(appId);
            if (accessToken == null)
                return oldTicket;

            // ticket还未过期
            String ticket = getFromStore(appId);

            if (ticket != null && !ticket.isEmpty()) {
                // 有效继续使用
                log.info("{} use oldTicket: {}", LOG_TAG, ticket);
                return ticket;
            }

            // ticket已过期
            WxCardTicket cardTicket = null;

            try {

                switch (config.getType()) {
                    case MP:
                        WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                        cardTicket = wxMpService.fetchCardTicket();
                        break;
                    default:
                        break;
                }

            } catch (WxErrorException e) {
                log.warn("{} request error: {}", LOG_TAG, e.getError());
                return oldTicket;
            }

            if (cardTicket == null || cardTicket.getTicket() == null || cardTicket.getTicket().isEmpty())
                return oldTicket;

            ticket = cardTicket.getTicket();

            setToStore(appId, ticket, cardTicket.getExpiresIn());

            return ticket;
        }
    }
}
