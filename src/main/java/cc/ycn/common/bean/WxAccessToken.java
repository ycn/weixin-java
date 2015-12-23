package cc.ycn.common.bean;

import cc.ycn.component.bean.WxAuthorizerAccessToken;
import cc.ycn.component.bean.WxComponentAccessToken;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/12/15.
 */
public class WxAccessToken implements Serializable {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private long expiresIn; // second

    public WxAccessToken() {

    }

    public WxAccessToken(WxComponentAccessToken componentAccessToken) {
        if (componentAccessToken != null) {
            this.accessToken = componentAccessToken.getComponentAccessToken();
            this.expiresIn = componentAccessToken.getExpiresIn();
        }
    }

    public WxAccessToken(WxAuthorizerAccessToken authorizerAccessToken) {
        if (authorizerAccessToken != null) {
            this.accessToken = authorizerAccessToken.getAuthorizerAccessToken();
            this.expiresIn = authorizerAccessToken.getExpiresIn();
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "WxAccessToken(access_token:" + accessToken + ",expires_in:" + expiresIn + ")";
    }
}
