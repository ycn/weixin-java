package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxComponentAccessTokenReq implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;

    @JSONField(name = "component_appsecret")
    private String componentAppSecret;

    @JSONField(name = "component_verify_ticket")
    private String componentVerifyTicket;

    public WxComponentAccessTokenReq() {

    }


    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getComponentAppSecret() {
        return componentAppSecret;
    }

    public void setComponentAppSecret(String componentAppSecret) {
        this.componentAppSecret = componentAppSecret;
    }

    public String getComponentVerifyTicket() {
        return componentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        this.componentVerifyTicket = componentVerifyTicket;
    }
}
