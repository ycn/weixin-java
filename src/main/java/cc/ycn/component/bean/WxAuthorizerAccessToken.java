package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxAuthorizerAccessToken implements Serializable {

    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;

    @JSONField(name = "authorizer_access_token")
    private String authorizerAccessToken;

    @JSONField(name = "expires_in")
    private long expiresIn;

    @JSONField(name = "authorizer_refresh_token")
    private String authorizerRefreshToken;

    public WxAuthorizerAccessToken() {

    }

    public String getAuthorizerAppId() {
        return authorizerAppId;
    }

    public void setAuthorizerAppId(String authorizerAppId) {
        this.authorizerAppId = authorizerAppId;
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }
}
