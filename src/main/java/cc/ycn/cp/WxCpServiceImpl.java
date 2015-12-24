package cc.ycn.cp;

import cc.ycn.common.bean.*;
import cc.ycn.common.bean.menu.WxMenu;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.cache.WxAccessTokenCache;
import cc.ycn.common.cache.WxConfigCache;
import cc.ycn.common.cache.WxJSTicketCache;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.common.util.StringTool;
import cc.ycn.cp.bean.WxDepartmentList;
import cc.ycn.cp.bean.WxQyUser;
import com.squareup.okhttp.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * 微信企业号Service
 *
 * @author andy
 */
public class WxCpServiceImpl implements WxCpService {

    private final static String LOG_TAG = "[WxCpService]";

    private String appId;
    private WxConfig config;
    private final RequestTool requestTool;

    public WxCpServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxConfigCache wxConfigCache = WxConfigCache.getInstance();
        this.config = wxConfigCache == null ? null : wxConfigCache.get(appId);
        if (this.config == null)
            throw new WxErrorException(new WxError(1001, "missing config:" + appId));

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(WxConstant.WX_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(WxConstant.WX_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(WxConstant.WX_WRITE_TIMEOUT, TimeUnit.SECONDS);

        requestTool = new RequestTool(LOG_TAG, httpClient);
    }

    @Override
    public String getAccessToken() {
        return WxAccessTokenCache.getInstance().get(appId);
    }

    @Override
    public WxAccessToken fetchAccessToken() {
        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={}&corpsecret={}";
        String url = StringTool.formatString(fUrl, appId, config.getAppSecret());

        return requestTool.get(
                "fetchAccessToken",
                url,
                WxAccessToken.class
        );
    }

    @Override
    public void verifyAccessToken(String accessToken) throws WxErrorException {
        getDepartments(accessToken);
    }

    @Override
    public WxDepartmentList getDepartments(String accessToken) throws WxErrorException {
        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token={}&id=1";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.get(
                "getDepartments",
                url,
                WxDepartmentList.class
        );
    }

    @Override
    public WxError sendMessage(WxMessage message) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={}";
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

        String fUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope={}&state={}#wechat_redirect";

        return StringTool.formatString(fUrl, appId, redirectUrl, scope.info(), state);
    }

    @Override
    public WxQyUser getUserId(String code) throws WxErrorException {
        if (code == null || code.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid code"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={}&code={}";
        String url = StringTool.formatString(fUrl, accessToken, code);

        return requestTool.get(
                "getUserId",
                url,
                WxQyUser.class
        );
    }

    @Override
    public WxOpenIdRef toOpenId(WxOpenIdRef openIdRef) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "toOpenId",
                url,
                WxOpenIdRef.class,
                ContentType.MEDIA_JSON,
                openIdRef
        );
    }

    @Override
    public WxOpenIdRef toUserId(WxOpenIdRef userIdRef) throws WxErrorException {
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token={}";
        String url = StringTool.formatString(fUrl, accessToken);

        return requestTool.post(
                "toUserId",
                url,
                WxOpenIdRef.class,
                ContentType.MEDIA_JSON,
                userIdRef
        );
    }

    @Override
    public WxError createMenu(String agentId, WxMenu menu) throws WxErrorException {
        if (agentId == null || agentId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid agentId"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token={}&agentid={}";
        String url = StringTool.formatString(fUrl, accessToken, agentId);

        return requestTool.post(
                "createMenu",
                url,
                WxError.class,
                ContentType.MEDIA_JSON,
                menu
        );
    }

    @Override
    public WxError deleteMenu(String agentId) throws WxErrorException {
        if (agentId == null || agentId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid agentId"));

        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            throw new WxErrorException(new WxError(1004, "invalid accessToken"));

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token={}&agentid={}";
        String url = StringTool.formatString(fUrl, accessToken, agentId);

        return requestTool.get(
                "deleteMenu",
                url,
                WxError.class
        );
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

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={}";
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

}
