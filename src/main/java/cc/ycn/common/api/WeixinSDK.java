package cc.ycn.common.api;

import cc.ycn.common.bean.*;
import cc.ycn.common.cache.*;
import cc.ycn.common.constant.WxCacheType;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.JsonConverter;
import cc.ycn.common.util.StringTool;
import cc.ycn.component.WxComponentServiceImpl;
import cc.ycn.component.bean.WxAuthorizerAccessToken;
import cc.ycn.component.bean.WxPreAuthCode;
import cc.ycn.cp.WxCpServiceImpl;
import cc.ycn.mp.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andy on 16/11/19.
 */
public class WeixinSDK {

    private final static Logger log = LoggerFactory.getLogger(WeixinSDK.class);
    private final static String LOG_TAG = "[WeixinSDK]";

    private static WxTokenHandler WX_TOKEN_HANDLER;
    private static WxMsgConfigCache wxMsgConfigCache;
    private static WxPayConfigCache wxPayConfigCache;
    private static WxRefreshTokenCache wxRefreshTokenCache;
    private static WxVerifyTicketCache wxVerifyTicketCache;
    private static WxAccessTokenCache wxAccessTokenCache;
    private static WxCardTicketCache wxCardTicketCache;
    private static WxJSTicketCache wxJSTicketCache;
    private static WxPreAuthCodeCache wxPreAuthCodeCache;

    public static void init(WxTokenHandler wxTokenHandler) throws WxErrorException {
        init(wxTokenHandler, 600, 10, 50000, 10);
    }

    public static void init(WxTokenHandler wxTokenHandler,
                            int refreshSeconds,
                            int concurrencyLevel,
                            long maximumSize,
                            int executorSize) throws WxErrorException {
        if (wxTokenHandler == null) {
            throw new WxErrorException(new WxError(1000, "WeixinSDK init failed!"));
        }

        wxMsgConfigCache = WxMsgConfigCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxPayConfigCache = WxPayConfigCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxRefreshTokenCache = WxRefreshTokenCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxVerifyTicketCache = WxVerifyTicketCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxAccessTokenCache = WxAccessTokenCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxCardTicketCache = WxCardTicketCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxJSTicketCache = WxJSTicketCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);
        wxPreAuthCodeCache = WxPreAuthCodeCache.init(wxTokenHandler, refreshSeconds, concurrencyLevel, maximumSize, executorSize);

        WX_TOKEN_HANDLER = wxTokenHandler;
    }

    public static WxMsgConfig fetchMsgConfig(String appId) {
        WxMsgConfig wxMsgConfig = null;
        WxToken wxToken = WX_TOKEN_HANDLER.get(WxCacheType.MSG_CONFIG.key(appId));
        if (wxToken == null) {
            log.error("{} (WX_FETCH) fetchMsgConfig: empty WxToken from store", LOG_TAG);
            return null;
        }
        if (StringTool.isEmpty(wxToken.getToken())) {
            log.error("{} (WX_FETCH) fetchMsgConfig: empty WxToken.token from store", LOG_TAG);
            return null;
        }
        if (wxToken.getExpiresIn() != 0) {
            log.error("{} (WX_FETCH) fetchMsgConfig: wrong WxToken.expiresIn from store! WxToken.expiresIn={}, should be 0", LOG_TAG, wxToken.getExpiresIn());
            return null;
        }
        wxMsgConfig = JsonConverter.json2pojo(wxToken.getToken(), WxMsgConfig.class);
        if (wxMsgConfig == null) {
            log.error("{} (WX_FETCH) fetchMsgConfig: decode WxToken.token from store failed! WxToken.token={}", LOG_TAG, wxToken.getToken());
            return null;
        }
        return wxMsgConfig;
    }

    public static WxPayConfig fetchPayConfig(String appId) {
        WxPayConfig wxPayConfig = null;
        WxToken wxToken = WX_TOKEN_HANDLER.get(WxCacheType.PAY_CONFIG.key(appId));
        if (wxToken == null) {
            log.error("{} (WX_FETCH) fetchPayConfig: empty WxToken from store", LOG_TAG);
            return null;
        }
        if (StringTool.isEmpty(wxToken.getToken())) {
            log.error("{} (WX_FETCH) fetchPayConfig: empty WxToken.token from store", LOG_TAG);
            return null;
        }
        if (wxToken.getExpiresIn() != 0) {
            log.error("{} (WX_FETCH) fetchPayConfig: wrong WxToken.expiresIn from store! WxToken.expiresIn={}, should be 0", LOG_TAG, wxToken.getExpiresIn());
            return null;
        }
        wxPayConfig = JsonConverter.json2pojo(wxToken.getToken(), WxPayConfig.class);
        if (wxPayConfig == null) {
            log.error("{} (WX_FETCH) fetchPayConfig: decode WxToken.token from store failed! WxToken.token={}", LOG_TAG, wxToken.getToken());
            return null;
        }
        return wxPayConfig;
    }

    public static String fetchRefreshToken(String appId) {
        return fetchToken(appId, WxCacheType.REFRESH_TOKEN, "fetchRefreshToken");
    }

    public static String fetchVerifyTicket(String appId) {
        return fetchToken(appId, WxCacheType.VERIFY_TICKET, "fetchVerifyTicket");
    }

    public static WxAccessToken fetchAccessToken(String appId) {
        String tokenKey = WxCacheType.ACCESS_TOKEN.key(appId);
        WxToken wxToken = WX_TOKEN_HANDLER.get(tokenKey);
        WxAccessToken oldAccessToken = new WxAccessToken(wxToken);
        String oldAccessTokenStr = JsonConverter.pojo2json(oldAccessToken);

        // 检查依赖
        if (!checkDeps(appId, oldAccessTokenStr, "fetchAccessToken")) {
            return oldAccessToken;
        }

        WxMsgConfig wxMsgConfig = fetchMsgConfig(appId);

        // 刷新accessToken
        WxAccessToken accessToken = null;

        try {

            switch (wxMsgConfig.getType()) {
                case MP:
                    if (!wxMsgConfig.isAuthorizer()) {
                        WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                        accessToken = wxMpService.fetchAccessToken();
                    } else {
                        WxComponentServiceImpl wxComponentService = new WxComponentServiceImpl(wxMsgConfig.getComAppid());
                        WxAuthorizerAccessToken authorizerAccessToken = wxComponentService.refreshAuthorizerAccessToken(appId);

                        // 更新refreshToken
                        WxRefreshTokenCache.getInstance().setToStore(appId, authorizerAccessToken.getAuthorizerRefreshToken(), 0);

                        accessToken = new WxAccessToken(authorizerAccessToken);
                    }
                    break;
                case CP:
                    WxCpServiceImpl wxCpService = new WxCpServiceImpl(appId);
                    accessToken = wxCpService.fetchAccessToken();
                    break;
                case COMPONENT:
                    WxComponentServiceImpl wxComponentService = new WxComponentServiceImpl(appId);
                    accessToken = new WxAccessToken(wxComponentService.fetchAccessToken());
                    break;
                default:
                    break;
            }

        } catch (WxErrorException e) {
            log.error("{} (WX_FETCH) fetchAccessToken request error:{}, appId:{}, use old:{}",
                    LOG_TAG, e.getError(), appId, oldAccessTokenStr);
            return oldAccessToken;
        }

        if (accessToken == null || accessToken.getAccessToken() == null || accessToken.getAccessToken().isEmpty()) {
            log.error("{} (WX_FETCH) fetchAccessToken empty resp, appId:{}, use old:{}",
                    LOG_TAG, appId, oldAccessTokenStr);
            return oldAccessToken;
        }

        log.info("{} (WX_FETCH) fetchAccessToken reload success! appId:{}, use new:{}, old:{}",
                LOG_TAG, appId, JsonConverter.pojo2json(accessToken), oldAccessTokenStr);

        return accessToken;
    }

    public static WxCardTicket fetchCardTicket(String appId) {
        String tokenKey = WxCacheType.CARD_TICKET.key(appId);
        WxToken wxToken = WX_TOKEN_HANDLER.get(tokenKey);
        WxCardTicket oldCardTicket = new WxCardTicket(wxToken);
        String oldCardTicketStr = JsonConverter.pojo2json(oldCardTicket);

        WxAccessToken wxAccessToken = new WxAccessToken(WX_TOKEN_HANDLER.get(WxCacheType.ACCESS_TOKEN.key(appId)));

        if (wxAccessToken.getAccessToken() == null) {
            log.error("{} (WX_FETCH) fetchCardTicket missing WxAccessToken, appId:{}, use old:{}",
                    LOG_TAG, appId, oldCardTicketStr);
            return oldCardTicket;
        }

        // 检查依赖
        if (!checkDeps(appId, oldCardTicketStr, "fetchCardTicket")) {
            return oldCardTicket;
        }

        WxMsgConfig wxMsgConfig = fetchMsgConfig(appId);

        // ticket已过期
        WxCardTicket cardTicket = null;

        try {

            switch (wxMsgConfig.getType()) {
                case MP:
                    WxMpServiceImpl wxMpService = new WxMpServiceImpl(appId);
                    cardTicket = wxMpService.fetchCardTicket();
                    break;
                default:
                    break;
            }

        } catch (WxErrorException e) {
            log.error("{} (WX_FETCH) fetchCardTicket request error:{}, appId:{}, use old:{}",
                    LOG_TAG, e.getError(), appId, oldCardTicketStr);
            return oldCardTicket;
        }

        if (cardTicket == null || cardTicket.getTicket() == null || cardTicket.getTicket().isEmpty()) {
            log.error("{} (WX_FETCH) fetchCardTicket empty resp, appId:{}, use old:{}",
                    LOG_TAG, appId, oldCardTicketStr);
            return oldCardTicket;
        }

        log.info("{} (WX_FETCH) fetchCardTicket reload success! appId:{}, use new:{}, old:{}",
                LOG_TAG, appId, JsonConverter.pojo2json(cardTicket), oldCardTicketStr);

        return cardTicket;
    }

    public static WxJSTicket fetchJSTicket(String appId) {
        String tokenKey = WxCacheType.JS_TICKET.key(appId);
        WxToken wxToken = WX_TOKEN_HANDLER.get(tokenKey);
        WxJSTicket oldJSTicket = new WxJSTicket(wxToken);
        String oldJSTicketStr = JsonConverter.pojo2json(oldJSTicket);

        WxAccessToken wxAccessToken = new WxAccessToken(WX_TOKEN_HANDLER.get(WxCacheType.ACCESS_TOKEN.key(appId)));

        if (wxAccessToken.getAccessToken() == null) {
            log.error("{} (WX_FETCH) fetchJSTicket missing WxAccessToken, appId:{}, use old:{}",
                    LOG_TAG, appId, oldJSTicketStr);
            return oldJSTicket;
        }

        // 检查依赖
        if (!checkDeps(appId, oldJSTicketStr, "fetchJSTicket")) {
            return oldJSTicket;
        }

        WxMsgConfig wxMsgConfig = fetchMsgConfig(appId);

        // ticket已过期
        WxJSTicket jsTicket = null;

        try {

            switch (wxMsgConfig.getType()) {
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
            log.error("{} (WX_FETCH) fetchCardTicket request error:{}, appId:{}, use old:{}",
                    LOG_TAG, e.getError(), appId, oldJSTicketStr);
            return oldJSTicket;
        }

        if (jsTicket == null || jsTicket.getTicket() == null || jsTicket.getTicket().isEmpty()) {
            log.error("{} (WX_FETCH) fetchCardTicket empty resp, appId:{}, use old:{}",
                    LOG_TAG, appId, oldJSTicketStr);
            return oldJSTicket;
        }

        log.info("{} (WX_FETCH) fetchCardTicket reload success! appId:{}, use new:{}, old:{}",
                LOG_TAG, appId, jsTicket, oldJSTicketStr);

        return jsTicket;
    }

    public static WxPreAuthCode fetchPreAuthCode(String appId) {
        String tokenKey = WxCacheType.PRE_AUTHCODE.key(appId);
        WxToken wxToken = WX_TOKEN_HANDLER.get(tokenKey);
        WxPreAuthCode oldPreAuthCode = new WxPreAuthCode(wxToken);
        String oldPreAuthCodeStr = JsonConverter.pojo2json(oldPreAuthCode);

        WxAccessToken wxAccessToken = new WxAccessToken(WX_TOKEN_HANDLER.get(WxCacheType.ACCESS_TOKEN.key(appId)));

        if (wxAccessToken.getAccessToken() == null) {
            log.error("{} (WX_FETCH) fetchPreAuthCode missing WxAccessToken, appId:{}, use old:{}",
                    LOG_TAG, appId, oldPreAuthCode);
            return oldPreAuthCode;
        }

        // 检查依赖
        if (!checkDeps(appId, oldPreAuthCodeStr, "fetchPreAuthCode")) {
            return oldPreAuthCode;
        }

        WxMsgConfig wxMsgConfig = fetchMsgConfig(appId);

        // code已过期
        WxPreAuthCode preAuthCode = null;


        try {

            switch (wxMsgConfig.getType()) {
                case COMPONENT:
                    WxComponentServiceImpl componentService = new WxComponentServiceImpl(appId);
                    preAuthCode = componentService.createPreAuthCode();
                    break;
                default:
                    break;
            }

        } catch (WxErrorException e) {
            log.error("{} (WX_FETCH) fetchPreAuthCode request error:{}, appId:{}, use old:{}",
                    LOG_TAG, e.getError(), appId, oldPreAuthCodeStr);
            return oldPreAuthCode;
        }

        if (preAuthCode == null || preAuthCode.getPreAuthCode() == null || preAuthCode.getPreAuthCode().isEmpty()) {
            log.error("{} (WX_FETCH) fetchPreAuthCode empty resp, appId:{}, use old:{}",
                    LOG_TAG, appId, oldPreAuthCodeStr);
            return oldPreAuthCode;
        }

        log.info("{} (WX_FETCH) fetchPreAuthCode reload success! appId:{}, use new:{}, old:{}",
                LOG_TAG, appId, JsonConverter.pojo2json(preAuthCode), oldPreAuthCodeStr);

        return preAuthCode;
    }

    private static boolean checkDeps(String appId, String oldTokenStr, String logTag) {
        if (appId == null || appId.isEmpty()) {
            log.error("{} (WX_FETCH) {} wrong request param: appId, use old:{}", LOG_TAG, logTag, oldTokenStr);
            return false;
        }

        // 检查微信配置信息
        WxMsgConfig wxMsgConfig = fetchMsgConfig(appId);
        if (wxMsgConfig == null) {
            log.error("{} (WX_FETCH) {} missing WxMsgConfig, appId:{}, use old:{}", LOG_TAG, appId, logTag, oldTokenStr);
            return false;
        }

        // 检查refreshToken
        if (wxMsgConfig.isAuthorizer()) {
            String refreshToken = fetchRefreshToken(appId);
            if (refreshToken == null || refreshToken.isEmpty()) {
                log.error("{} (WX_FETCH) {} missing refreshToken, appId:{}, use old:{}", LOG_TAG, appId, logTag, oldTokenStr);
                return false;
            }
        }

        return true;
    }

    private static String fetchToken(String appId, WxCacheType prefix, String logTag) {
        String token = null;
        WxToken wxToken = WX_TOKEN_HANDLER.get(prefix.key(appId));
        if (wxToken == null) {
            log.error("{} (WX_FETCH) {}: empty WxToken from store", LOG_TAG, logTag);
            return null;
        }
        if (StringTool.isEmpty(wxToken.getToken())) {
            log.error("{} (WX_FETCH) {}: empty WxToken.token from store", LOG_TAG, logTag);
            return null;
        }
        if (wxToken.getExpiresIn() != 0) {
            log.error("{} (WX_FETCH) {}: wrong WxToken.expiresIn from store! WxToken.expiresIn={}, should be 0", LOG_TAG, logTag, wxToken.getExpiresIn());
            return null;
        }
        token = wxToken.getToken();
        if (token == null) {
            log.error("{} (WX_FETCH) {}: decode WxToken.token from store failed! WxToken.token={}", LOG_TAG, logTag, wxToken.getToken());
            return null;
        }
        return token;
    }
}
