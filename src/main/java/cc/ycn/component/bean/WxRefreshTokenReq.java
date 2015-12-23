package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxRefreshTokenReq implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;

    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;

    @JSONField(name = "authorizer_refresh_token")
    private String authorizerRefreshToken;

    public WxRefreshTokenReq() {

    }


    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getAuthorizerAppId() {
        return authorizerAppId;
    }

    public void setAuthorizerAppId(String authorizerAppId) {
        this.authorizerAppId = authorizerAppId;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }
}
