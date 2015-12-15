package cc.ycn.cp;

import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.cache.WxAccessTokenCache;
import cc.ycn.common.cache.WxConfigCache;
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
        this.config = wxConfigCache == null ? null : wxConfigCache.getConfig(appId);
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
        return WxAccessTokenCache.getInstance().getToken(appId);
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

        String fUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={}&code={}";
        String url = StringTool.formatString(fUrl, accessToken, code);

        return requestTool.get(
                "getUserId",
                url,
                WxQyUser.class
        );
    }


}
