package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxJSTicket;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.cp.WxCpServiceImpl;
import cc.ycn.mp.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/17/15.
 */
public class WxJSTicketCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxJSTicketCache.class);
    private final static String LOG_TAG = "[WxJSTicketCache]";
    private static final AtomicReference<WxJSTicketCache> instance = new AtomicReference<WxJSTicketCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxJSTicketCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxJSTicketCache getInstance() {
        return instance.get();
    }

    private WxJSTicketCache(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize,
                            boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxJSTicketCacheLoader(executorSize, readonly),
                CacheKeyPrefix.JS_TICKET,
                readonly
        );
    }

    class WxJSTicketCacheLoader extends WxCacheLoader<String> {

        public WxJSTicketCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected String loadOneReadonly(String appId, String oldTicket, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            String ticket = getFromStore(appId);
            log.info("{} reload success! (readonly) appId:{}, use newJSTicket:{}, oldJSTicket:{}", LOG_TAG, appId, ticket, oldTicket);
            return ticket == null ? oldTicket : ticket;
        }

        @Override
        protected String loadOne(String appId, String oldTicket, boolean sync) {
            if (appId == null || appId.isEmpty())
                return null;

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null) {
                log.warn("{} missing config. appId:{}, use oldJSTicket:{}", LOG_TAG, appId, oldTicket);
                return oldTicket;
            }

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.get(appId);
            if (accessToken == null) {
                log.warn("{} missing AccessToken. appId:{}, use oldJSTicket:{}", LOG_TAG, appId, oldTicket);
                return oldTicket;
            }

            // ticket还未过期
            String ticket = getFromStore(appId);

            if (ticket != null && !ticket.isEmpty()) {
                // 有效继续使用
                log.info("{} appId:{}, reuse oldJSTicket:{}", LOG_TAG, appId, ticket);
                return ticket;
            }

            // ticket已过期
            WxJSTicket jsTicket = null;

            try {

                switch (config.getType()) {
                    case MP:
                        WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                        jsTicket = wxMpService.fetchJSTicket();
                        break;
                    case CP:
                        WxCpServiceImpl wxCpService = new WxCpServiceImpl(appId);
                        jsTicket = wxCpService.fetchJSTicket();
                        break;
                    default:
                        break;
                }

            } catch (WxErrorException e) {
                log.warn("{} request error:{}, appId:{}, use oldJSTicket:{}", LOG_TAG, e.getError(), appId, oldTicket);
                return oldTicket;
            }

            if (jsTicket == null || jsTicket.getTicket() == null || jsTicket.getTicket().isEmpty()) {
                log.warn("{} empty resp, appId:{}, use oldJSTicket:{}", LOG_TAG, appId, oldTicket);
                return oldTicket;
            }

            ticket = jsTicket.getTicket();

            setToStore(appId, ticket, jsTicket.getExpiresIn());

            log.info("{} reload success! appId:{}, use newJSTicket:{}, oldJSTicket:{}", LOG_TAG, appId, ticket, oldTicket);
            return ticket;
        }
    }
}
