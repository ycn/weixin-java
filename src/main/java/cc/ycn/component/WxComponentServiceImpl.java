package cc.ycn.component;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.cache.*;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.common.util.StringTool;
import cc.ycn.component.bean.*;
import com.squareup.okhttp.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * 微信开放平台Service
 *
 * @author andy
 */
public class WxComponentServiceImpl implements WxComponentService {

    private final static String LOG_TAG = "[WxComponentService]";

    private String appId;
    private WxMsgConfig config;
    private final RequestTool requestTool;

    public WxComponentServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxMsgConfigCache wxMsgConfigCache = WxMsgConfigCache.getInstance();
        this.config = wxMsgConfigCache == null ? null : wxMsgConfigCache.get(appId);
        if (this.config == null) {
            throw new WxErrorException(new WxError(1001, "missing config:" + appId));
        }

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(WxConstant.WX_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(WxConstant.WX_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(WxConstant.WX_WRITE_TIMEOUT, TimeUnit.SECONDS);

        requestTool = new RequestTool(LOG_TAG, httpClient);
    }

    @Override
    public WxMsgConfig getConfig() {
        return config;
    }

    @Override
    public String getVerifyTicket() {
        return WxVerifyTicketCache.getInstance().get(appId);
    }

    @Override
    public String getAccessToken() {
        return WxAccessTokenCache.getInstance().get(appId);
    }

    @Override
    public WxComponentAccessToken fetchAccessToken() throws WxErrorException {
        String verifyTicket = getVerifyTicket();

        if (verifyTicket == null || verifyTicket.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component verifyTicket"));

        WxComponentAccessTokenReq req = new WxComponentAccessTokenReq();
        req.setComponentAppId(appId);
        req.setComponentAppSecret(config.getSecret());
        req.setComponentVerifyTicket(verifyTicket);

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

        return requestTool.post(
                "fetchAccessToken",
                url,
                WxComponentAccessToken.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public void verifyAccessToken(String accessToken) throws WxErrorException {
        // TODO implements WxComponentServiceImpl::verifyAccessToken method
    }

    @Override
    public String getPreAuthCode() {
        return WxPreAuthCodeCache.getInstance().get(appId);
    }

    @Override
    public WxPreAuthCode createPreAuthCode() throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "createPreAuthCode",
                url,
                WxPreAuthCode.class,
                ContentType.MEDIA_JSON,
                new WxComponentAppIdRef(appId)
        );
    }

    @Override
    public String getOAuthUrl(String redirectUrl) throws WxErrorException {
        if (redirectUrl == null || redirectUrl.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid redirectUrl"));

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
            throw new WxErrorException(new WxError(1003, "invalid redirectUrl"));
        }

        // 获取预授权码
        String code = getPreAuthCode();
        if (code == null || code.isEmpty())
            throw new WxErrorException(new WxError(1004, "wrong preAuthCode"));

        String fUrl = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid={}&pre_auth_code={}&redirect_uri={}";

        return StringTool.formatString(fUrl, this.appId, code, redirectUrl);
    }

    @Override
    public String getAuthorizerAccessToken(String appId) {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        return WxAccessTokenCache.getInstance().get(appId);
    }

    @Override
    public String getAuthorizerRefreshToken(String appId) {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        return WxRefreshTokenCache.getInstance().get(appId);
    }

    @Override
    public WxAuthorizerAccessInfo fetchAuthorizerAccessToken(String authCode) throws WxErrorException {
        if (authCode == null || authCode.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid authCode"));

        WxAuthCodeRef req = new WxAuthCodeRef();
        req.setComponentAppId(appId);
        req.setAuthorizationCode(authCode);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        WxAuthorizerAccessInfo accessInfo = requestTool.post(
                "fetchAuthorizerAccessToken",
                url,
                WxAuthorizerAccessInfo.class,
                ContentType.MEDIA_JSON,
                req
        );

        // 更新AccessToken
        if (accessInfo != null) {
            WxAuthorizerAccessToken authorizationInfo = accessInfo.getAuthorizationInfo();
            if (authorizationInfo != null) {
                WxAccessTokenCache.getInstance().setToStore(
                        authorizationInfo.getAuthorizerAppId(),
                        authorizationInfo.getAuthorizerAccessToken(),
                        authorizationInfo.getExpiresIn()
                );
            }
        }

        return accessInfo;
    }

    @Override
    public WxAuthorizerAccessToken refreshAuthorizerAccessToken(String appId) throws WxErrorException {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        String refreshToken = getAuthorizerRefreshToken(appId);
        if (refreshToken == null || refreshToken.isEmpty())
            throw new WxErrorException(new WxError(1001, "missing refreshToken"));

        WxRefreshTokenReq req = new WxRefreshTokenReq();
        req.setComponentAppId(this.appId);
        req.setAuthorizerAppId(appId);
        req.setAuthorizerRefreshToken(refreshToken);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "refreshAuthorizerAccessToken",
                url,
                WxAuthorizerAccessToken.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public WxAuthorizerInfoResp getAuthorizerInfo(String appId) throws WxErrorException {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "getAuthorizerInfo",
                url,
                WxAuthorizerInfoResp.class,
                ContentType.MEDIA_JSON,
                new WxAuthorizerAppIdRef(this.appId, appId)
        );
    }

    @Override
    public WxOptionValue getAuthorizerOption(String appId, String optionName) throws WxErrorException {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        if (optionName == null || optionName.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid optionName"));

        WxOptionValue req = new WxOptionValue();
        req.setComponentAppId(this.appId);
        req.setAuthorizerAppId(appId);
        req.setOptionName(optionName);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "getAuthorizerOption",
                url,
                WxOptionValue.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public WxError setAuthorizerOption(String appId, String optionName, String optionValue) throws WxErrorException {
        if (appId == null || appId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid appId"));

        if (optionName == null || optionName.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid optionName"));

        if (optionValue == null || optionValue.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid optionValue"));

        WxOptionValue req = new WxOptionValue();
        req.setComponentAppId(this.appId);
        req.setAuthorizerAppId(appId);
        req.setOptionName(optionName);
        req.setOptionValue(optionValue);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/component/api_set_authorizer_option?component_access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "setAuthorizerOption",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                req
        );
    }
}
