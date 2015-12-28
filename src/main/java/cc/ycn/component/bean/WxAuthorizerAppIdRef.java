package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAuthorizerAppIdRef implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;

    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;

    public WxAuthorizerAppIdRef() {

    }

    public WxAuthorizerAppIdRef(String componentAppId, String authorizerAppId) {
        this.componentAppId = componentAppId;
        this.authorizerAppId = authorizerAppId;
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
}
