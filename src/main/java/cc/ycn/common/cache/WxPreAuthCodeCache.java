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
                            int executorSize,
                            boolean readonly) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxPreAuthCodeCache(centralStore,
                    refreshSeconds, concurrencyLevel, maximumSize, executorSize, readonly));
    }

    public static WxPreAuthCodeCache getInstance() {
        return instance.get();
    }

    private WxPreAuthCodeCache(CentralStore centralStore,
                               int refreshSeconds,
                               int concurrencyLevel,
                               long maximumSize,
                               int executorSize,
                               boolean readonly) {
        init(centralStore,
                refreshSeconds,
                concurrencyLevel,
                maximumSize,
                new WxPreAuthCodeCacheLoader(executorSize, readonly),
                CacheKeyPrefix.PRE_AUTHCODE,
                readonly
        );
    }

    class WxPreAuthCodeCacheLoader extends WxCacheLoader<String> {

        public WxPreAuthCodeCacheLoader(int executorSize, boolean readonly) {
            super(executorSize, readonly);
        }

        @Override
        protected String loadOneReadonly(String appId, String oldCode, boolean sync) {
            if (oldCode == null)
                oldCode = "";

            if (appId == null || appId.isEmpty())
                return oldCode;

            String code = getFromStore(appId);
            log.info("{} reload success! (readonly) appId:{}, use newPreAuthCode:{}, oldPreAuthCode:{}", LOG_TAG, appId, code, oldCode);
            return code == null ? oldCode : code;
        }

        @Override
        protected String loadOne(String appId, String oldCode, boolean sync) {
            if (oldCode == null)
                oldCode = "";

            if (appId == null || appId.isEmpty())
                return oldCode;

            // 检查微信配置信息
            WxConfigCache wxConfigCache = WxConfigCache.getInstance();
            WxConfig config = wxConfigCache == null ? null : wxConfigCache.get(appId);
            if (config == null) {
                log.warn("{} missing config. appId:{}, use oldPreAuthCode:{}", LOG_TAG, appId, oldCode);
                return oldCode;
            }

            // 检查AccessToken
            WxAccessTokenCache wxAccessTokenCache = WxAccessTokenCache.getInstance();
            String accessToken = wxAccessTokenCache == null ? null : wxAccessTokenCache.get(appId);
            if (accessToken == null) {
                log.warn("{} missing AccessToken. appId:{}, use oldPreAuthCode:{}", LOG_TAG, appId, oldCode);
                return oldCode;
            }

            // code还未过期
            String code = getFromStore(appId);

            if (code != null && !code.isEmpty()) {
                // 有效继续使用
                log.info("{} appId:{}, reuse oldPreAuthCode:{}", LOG_TAG, appId, code);
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
                log.warn("{} request error:{}, appId:{}, use oldPreAuthCode:{}", LOG_TAG, e.getError(), appId, oldCode);
                return oldCode;
            }

            if (preAuthCode == null || preAuthCode.getPreAuthCode() == null || preAuthCode.getPreAuthCode().isEmpty()) {
                log.warn("{} empty resp, appId:{}, use oldPreAuthCode:{}", LOG_TAG, appId, oldCode);
                return oldCode;
            }

            code = preAuthCode.getPreAuthCode();

            setToStore(appId, code, preAuthCode.getExpiresIn());

            log.info("{} reload success! appId:{}, use newPreAuthCode:{}, oldPreAuthCode:{}", LOG_TAG, appId, code, oldCode);
            return code;
        }
    }
}
