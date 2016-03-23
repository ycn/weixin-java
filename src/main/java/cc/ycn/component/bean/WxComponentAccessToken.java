package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxComponentAccessToken implements Serializable {

    @JSONField(name = "component_access_token")
    private String componentAccessToken;

    @JSONField(name = "expires_in")
    private long expiresIn; // second

    public WxComponentAccessToken() {

    }

    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "WxComponentAccessToken(access_token:" + componentAccessToken + ",expires_in:" + expiresIn + ")";
    }

}
