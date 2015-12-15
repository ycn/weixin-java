package cc.ycn.mp;

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
import cc.ycn.mp.bean.WxIpList;
import cc.ycn.mp.bean.WxKfAccount;
import cc.ycn.mp.bean.WxOAuthAccessToken;
import com.squareup.okhttp.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * 微信公众号Service
 *
 * @author andy
 */
public class WxMpServiceImpl implements WxMpService {

    private final static String LOG_TAG = "[WxMpService]";

    private String appId;
    private WxConfig config;
    private final RequestTool requestTool;

    public WxMpServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxConfigCache wxConfigCache = WxConfigCache.getInstance();
        this.config = wxConfigCache == null ? null : wxConfigCache.getConfig(appId);
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
    public String getAccessToken() {
        return WxAccessTokenCache.getInstance().getToken(appId);
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
        String accessToken = getAccessToken();

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
    public WxError createKfAccount(WxKfAccount account) throws WxErrorException {
        String accessToken = getAccessToken();

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

        String fUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope={}&state={}#wechat_redirect";

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
}
