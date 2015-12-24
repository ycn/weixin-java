package cc.ycn.common.cache;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.constant.CacheKeyPrefix;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.component.WxComponentServiceImpl;
import cc.ycn.component.bean.WxPreAuthCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/24/15.
 */
public class WxPreAuthCodeCache extends ExpireCache<String> {
    private final static Logger log = LoggerFactory.getLogger(WxPreAuthCodeCache.class);
    private final static String LOG_TAG = "[WxPreAuthCodeCache]";
    private static final AtomicReference<WxPreAuthCodeCache> instance = new AtomicReference<WxPreAuthCodeCache>();

    public static void init(CentralStore centralStore,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxPreAuthCodeCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize));
    }

    public static WxPreAuthCodeCache getInstance() {
        return instance.get();
    }

    private WxPreAuthCodeCache(CentralStore centralStore,
                               int refreshSeconds,
                               int concurrencyLevel,
                               long maximumSize,
                               int executorSize) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxPreAuthCodeCacheLoader(executorSize),
                CacheKeyPrefix.PRE_AUTHCODE
        );
    }

    class WxPreAuthCodeCacheLoader extends WxCacheLoader<String> {

        public WxPreAuthCodeCacheLoader(int executorSize) {
            super(executorSize);
        }

        @Override
        protected String loadOne(String appId, String oldCode, boolean sync) {
            if (appId == null || appId.isEmpty())
                return "";

            if (oldCode == null)
                oldCode = "";

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null)
                return oldCode;

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.get(appId);
            if (accessToken == null)
                return oldCode;

            // code还未过期
            String code = getFromStore(appId);

            if (code != null && !code.isEmpty()) {
                // 有效继续使用
                log.info("{} use oldCode: {}", LOG_TAG, code);
                return code;
            }

            // code已过期
            WxPreAuthCode preAuthCode = null;


            try {

                switch (config.getType()) {
                    case COMPONENT:
                        WxComponentServiceImpl componentService = new WxComponentServiceImpl(appId);
                        preAuthCode = componentService.createPreAuthCode();
                        break;
                    default:
                        break;
                }

            } catch (WxErrorException e) {
                log.warn("{} request error: {}", LOG_TAG, e.getError());
                return oldCode;
            }

            if (preAuthCode == null || preAuthCode.getPreAuthCode() == null || preAuthCode.getPreAuthCode().isEmpty())
                return oldCode;

            code = preAuthCode.getPreAuthCode();

            setToStore(appId, code, preAuthCode.getExpiresIn());

            return code;
        }
    }
}
