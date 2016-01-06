package cc.ycn.wp;

import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
import cc.ycn.common.cache.WxConfigCache;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.common.util.StringTool;
import cc.ycn.mp.bean.WxOAuthAccessToken;
import cc.ycn.mp.bean.WxUserInfo;
import com.squareup.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by andy on 1/6/16.
 */
public class WxWpServiceImpl implements WxWpService {

    private final static Logger log = LoggerFactory.getLogger(WxWpServiceImpl.class);
    private final static String LOG_TAG = "[WxWpService]";

    private String appId;
    private WxConfig config;
    private final RequestTool requestTool;

    public WxWpServiceImpl(String appId) throws WxErrorException {
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

        requestTool = new RequestTool(LOG_TAG, httpClient);
    }

    @Override
    public WxConfig getConfig() {
        return config;
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

        String fUrl = "https://open.weixin.qq.com/connect/qrconnect?appid={}&redirect_uri={}&response_type=code&scope={}&state={}#wechat_redirect";
        return StringTool.formatString(fUrl, appId, redirectUrl, scope.info(), state);
    }

    @Override
    public WxOAuthAccessToken getOAuthAccessToken(String code) throws WxErrorException {
        if (code == null || code.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid code"));

        String fUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
        String url = StringTool.formatString(fUrl, appId, config.getAppSecret(), code);

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
    public WxError checkOAuthAccessToken(String oauthAccessToken, String openId) throws WxErrorException {
        if (oauthAccessToken == null || oauthAccessToken.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid oauthAccessToken"));

        if (openId == null || openId.isEmpty())
            throw new WxErrorException(new WxError(1003, "invalid openId"));

        String fUrl = "https://api.weixin.qq.com/sns/auth?access_token={}&openid={}";
        String url = StringTool.formatString(fUrl, oauthAccessToken, openId);

        return requestTool.get(
                "checkOAuthAccessToken",
                url,
                WxError.class
        );
    }
}
