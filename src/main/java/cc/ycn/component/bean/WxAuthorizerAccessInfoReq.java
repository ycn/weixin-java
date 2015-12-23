package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxAuthorizerAccessInfoReq implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;

    @JSONField(name = "authorization_code")
    private String authorizationCode;

    public WxAuthorizerAccessInfoReq() {

    }

    public WxAuthorizerAccessInfoReq(String componentAppId, String authorizationCode) {
        this.componentAppId = componentAppId;
        this.authorizationCode = authorizationCode;
    }


    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
