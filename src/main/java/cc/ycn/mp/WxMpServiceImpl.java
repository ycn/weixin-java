package cc.ycn.mp;

import cc.ycn.common.api.WxErrorHandler;
import cc.ycn.common.bean.*;
import cc.ycn.common.bean.menu.WxMenu;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.cache.*;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.common.util.StringTool;
import cc.ycn.mp.bean.*;
import com.squareup.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * 微信公众号Service
 *
 * @author andy
 */
public class WxMpServiceImpl implements WxMpService, WxErrorHandler {

    private final static Logger log = LoggerFactory.getLogger(WxMpServiceImpl.class);
    private final static String LOG_TAG = "[WxMpService]";

    private String appId;
    private WxConfig config;
    private final RequestTool requestTool;

    public WxMpServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxConfigCache wxConfigCache = WxConfigCache.getInstance();
        this.config = wxConfigCache == null ? null : wxConfigCache.get(appId);
        if (this.config == null) {
            throw new WxErrorException(new WxError(1001, "missing config:" + appId));
        }

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(WxConstant.WX_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(WxConstant.WX_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(WxConstant.WX_WRITE_TIMEOUT, TimeUnit.SECONDS);

        requestTool = new RequestTool(LOG_TAG, httpClient, this);
    }

    @Override
    public WxConfig getConfig() {
        return config;
    }

    @Override
    public String getAccessToken() {
        return WxAccessTokenCache.getInstance().get(appId);
    }

    @Override
    public WxAccessToken fetchAccessToken() throws WxErrorException {
        String fUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={}&secret={}";
        String url = StringTool.formatString(fUrl, appId, config.getAppSecret());

        return requestTool.get(
                "fetchAccessToken",
                url,
                WxAccessToken.class
        );
    }

    @Override
    public void verifyAccessToken(String accessToken) throws WxErrorException {
        getIPList(accessToken);
    }

    @Override
    public WxIpList getIPList(String accessToken) throws WxErrorException {
        if (accessToken == null || accessToken.isEmpty()) {
            accessToken = getAccessToken();
        }

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "getIPList",
                url,
                WxIpList.class
        );
    }

    @Override
    public WxError sendMessage(WxMessage message) throws WxErrorException {
        if (message == null)
            throw new WxErrorException(new WxError(1004, "invalid message type"));

        // 发送模板消息
        if (message.getMsgType() == null) {
            WxError error = null;
            try {
                WxMsgIdRef idRef = sendTemplateMessage(message);
                error = new WxError(0, idRef.getMsgId());
            } catch (WxErrorException e) {
                error = e.getError();
            }
            return error;
        }

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "sendMessage",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                message
        );
    }

    @Override
    public WxMsgIdRef sendTemplateMessage(WxMessage message) throws WxErrorException {
        if (message == null || message.getMsgType() != null)
            throw new WxErrorException(new WxError(1004, "invalid message type"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "sendTemplateMessage",
                url,
                WxMsgIdRef.class,
                ContentType.MEDIA_JSON,
                message
        );
    }

    @Override
    public WxError setIndustry(String industryId1, String industryId2) throws WxErrorException {
        if (industryId1 == null || industryId1.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid industryId1"));

        if (industryId2 == null || industryId2.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid industryId2"));

        WxIndustryId req = new WxIndustryId();
        req.setIndustryId1(industryId1);
        req.setIndustryId2(industryId2);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "setIndustry",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public WxTemplateIdRef addTemplate(String templateIdShort) throws WxErrorException {
        if (templateIdShort == null || templateIdShort.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid templateIdShort"));

        WxShortTemplateIdRef req = new WxShortTemplateIdRef();
        req.setTemplateIdShort(templateIdShort);

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "addTemplate",
                url,
                WxTemplateIdRef.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public WxTemplateInfo getTemplateInfo() throws WxErrorException {

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "getTemplateInfo",
                url,
                WxTemplateInfo.class
        );
    }

    @Override
    public WxError delTemplate(String templateId) throws WxErrorException {
        if (StringTool.isEmpty(templateId))
            throw new WxErrorException(new WxError(1004, "invalid templateId"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        WxTemplateIdRef req = new WxTemplateIdRef(templateId);

        String fUrl = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "delTemplate",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                req
        );
    }

    @Override
    public WxError createKfAccount(WxKfAccount account) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "createKfAccount",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                account
        );
    }

    @Override
    public WxError updateKfAccount(WxKfAccount account) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "updateKfAccount",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                account
        );
    }

    @Override
    public WxError deleteKfAccount(WxKfAccount account) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "deleteKfAccount",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                account
        );
    }

    @Override
    public String getOAuthUrl(String state, WxOAuthScope scope, String redirectUrl) throws WxErrorException {
        if (state == null || state.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid state"));

        if (scope == null)
            throw new WxErrorException(new WxError(1003, "invalid scope"));

        if (redirectUrl == null || redirectUrl.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid redirectUrl"));

        try {
            state = URLEncoder.encode(state, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
            throw new WxErrorException(new WxError(1003, "invalid state"));
        }

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
            throw new WxErrorException(new WxError(1003, "invalid redirectUrl"));
        }

        String appendix = "";
        if (config.isAuthorizer()) {
            appendix = "&component_appid=" + config.getComponentAppId();
        }

        String fUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope={}&state={}{}#wechat_redirect";
        return StringTool.formatString(fUrl, appId, redirectUrl, scope.info(), state, appendix);
    }

    @Override
    public WxOAuthAccessToken getOAuthAccessToken(String code) throws WxErrorException {
        if (code == null || code.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid code"));

        String fUrl = "";
        String url = "";

        if (config.isAuthorizer()) {

            String accessToken = WxAccessTokenCache.getInstance().get(config.getComponentAppId());

            if (accessToken == null || accessToken.isEmpty())
                throw new WxErrorException(new WxError(1004, "invalid component accessToken"));

            fUrl = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid={}&code={}&grant_type=authorization_code&component_appid={}&component_access_token={}";
            url = StringTool.formatString(fUrl, appId, code, config.getComponentAppId(), accessToken);
        } else {
            fUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
            url = StringTool.formatString(fUrl, appId, config.getAppSecret(), code);
        }

        return requestTool.get(
                "getOAuthAccessToken",
                url,
                WxOAuthAccessToken.class
        );
    }

    @Override
    public WxUserInfo getUserInfo(String oauthAccessToken, String openId) throws WxErrorException {
        if (oauthAccessToken == null || oauthAccessToken.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid oauthAccessToken"));

        if (openId == null || openId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid openId"));

        String fUrl = "https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}&lang=zh_CN";
        String url = StringTool.formatString(fUrl, oauthAccessToken, openId);

        return requestTool.get(
                "getUserInfo",
                url,
                WxUserInfo.class
        );
    }

    @Override
    public WxUserInfo getUserInfo(String openId) throws WxErrorException {

        String accessToken = getAccessToken();

        if (openId == null || openId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid openId"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={}&openid={}&lang=zh_CN ";
        String url = StringTool.formatString(fUrl, accessToken, openId);

        return requestTool.get(
                "getUserInfo",
                url,
                WxUserInfo.class
        );
    }

    @Override
    public WxError checkOAuthAccessToken(String oauthAccessToken, String openId) throws WxErrorException {
        if (StringTool.isEmpty(oauthAccessToken))
            throw new WxErrorException(new WxError(1003, "invalid oauthAccessToken"));

        if (StringTool.isEmpty(openId))
            throw new WxErrorException(new WxError(1003, "invalid openId"));

        String fUrl = "https://api.weixin.qq.com/sns/auth?access_token={}&openid={}";
        String url = StringTool.formatString(fUrl, oauthAccessToken, openId);

        return requestTool.get(
                "checkOAuthAccessToken",
                url,
                WxError.class
        );
    }

    @Override
    public WxError createMenu(WxMenu menu) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "createMenu",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                menu
        );
    }

    @Override
    public WxError deleteMenu() throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "deleteMenu",
                url,
                WxError.class
        );
    }

    @Override
    public WxError createMatchMenu(WxMenu menu) throws WxErrorException {
        if (menu == null || menu.getMatchRule() == null)
            throw new WxErrorException(new WxError(1003, "invalid matchRule"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "createMatchMenu",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                menu
        );
    }

    @Override
    public WxQRTicket createQRCode(WxScanScene scanScene) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "createQRCode",
                url,
                WxQRTicket.class,
                ContentType.MEDIA_JSON,
                scanScene
        );
    }

    @Override
    public String showQRCode(String ticket) throws WxErrorException {
        String encodedTicket = "";
        try {
            encodedTicket = URLEncoder.encode(ticket, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + encodedTicket;
    }

    @Override
    public String toShortUrl(String longUrl) throws WxErrorException {
        if (longUrl == null || longUrl.isEmpty())
            return "";

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        WxLongUrl wxLongUrl = new WxLongUrl();
        wxLongUrl.setAction("long2short");
        wxLongUrl.setLongUrl(longUrl);

        WxShortUrl wxShortUrl = requestTool.post(
                "toShortUrl",
                url,
                WxShortUrl.class,
                ContentType.MEDIA_JSON,
                wxLongUrl
        );

        return wxShortUrl == null ? "" : wxShortUrl.getShortUrl();
    }

    @Override
    public String getJSTicket() {
        return WxJSTicketCache.getInstance().get(appId);
    }

    @Override
    public WxJSTicket fetchJSTicket() throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={}&type=jsapi";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "fetchJSTicket",
                url,
                WxJSTicket.class
        );
    }

    @Override
    public WxJSSignature createJSSignature(String url) {
        if (url == null || url.isEmpty())
            return null;

        int ts = (int) (System.currentTimeMillis() / 1000);

        WxJSSignature sign = new WxJSSignature();
        sign.setAppId(appId);
        sign.setNonceStr(StringTool.getRandomStr(16));
        sign.setTimeStamp(ts + "");
        sign.setUrl(url);

        String ticket = getJSTicket();
        sign.createSignature(ticket);

        return sign;
    }

    @Override
    public String getCardTicket() {
        return WxCardTicketCache.getInstance().get(appId);
    }

    @Override
    public WxCardTicket fetchCardTicket() throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={}&type=wx_card";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "fetchCardTicket",
                url,
                WxCardTicket.class
        );
    }

    @Override
    public WxWifiConnectUrl getConnectUrl() throws WxErrorException {

        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://api.weixin.qq.com/bizwifi/account/get_connecturl?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "connectUrl",
                url,
                WxWifiConnectUrl.class
        );
    }

    @Override
    public WxMediaListResp wxMaterialReq(WxMediaListReq wxMediaListReq) throws WxErrorException {

        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));


        String fUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);


        return requestTool.post(
                "wxMaterialReq",
                url,
                WxMediaListResp.class,
                ContentType.MEDIA_JSON,
                wxMediaListReq
        );
    }

    @Override
    public boolean shouldRetryOnWxError(WxError error) {
        if (error == null) return false;

        switch ((int) error.getErrcode()) {
            // access_token无效
            case 40001:
            case 40014:
            case 41001:
            case 42001:
                WxAccessTokenCache.getInstance().refresh(appId);
                return true;
            // refresh_token无效
            case 40030:
            case 41003:
            case 42002:
                WxRefreshTokenCache.getInstance().refresh(appId);
                return true;
        }

        return false;
    }

    @Override
    public String getRetryUrl(String url, WxError error) {
        String retryUrl = url;

        if (error == null) return retryUrl;

        switch ((int) error.getErrcode()) {
            // access_token无效
            case 40001:
            case 40014:
            case 41001:
            case 42001:
                Matcher matcher = WxConstant.ACCESS_TOKEN_PARAM_PATTERN.matcher(url);
                if (matcher.find()) {
                    String accessToken = WxAccessTokenCache.getInstance().get(appId);
                    retryUrl = url.replace(matcher.group(1), accessToken);
                }
                break;
        }

        return retryUrl;
    }
}
