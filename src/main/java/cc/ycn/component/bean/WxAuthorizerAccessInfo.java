package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxAuthorizerAccessInfo implements Serializable {

    @JSONField(name = "authorization_info")
    private WxAuthorizerAccessToken authorizationInfo;

    public WxAuthorizerAccessInfo() {

    }


    public WxAuthorizerAccessToken getAuthorizationInfo() {
        return authorizationInfo;
    }

    public void setAuthorizationInfo(WxAuthorizerAccessToken authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }
}
