package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxAuthorizerInfoResp implements Serializable {

    @JSONField(name = "authorizer_info")
    private WxAuthorizerInfo authorizerInfo;

    @JSONField(name = "authorization_info")
    private WxAuthorizationInfo authorizationInfo;

    public WxAuthorizerInfoResp() {

    }


    public WxAuthorizerInfo getAuthorizerInfo() {
        return authorizerInfo;
    }

    public void setAuthorizerInfo(WxAuthorizerInfo authorizerInfo) {
        this.authorizerInfo = authorizerInfo;
    }

    public WxAuthorizationInfo getAuthorizationInfo() {
        return authorizationInfo;
    }

    public void setAuthorizationInfo(WxAuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }
}
