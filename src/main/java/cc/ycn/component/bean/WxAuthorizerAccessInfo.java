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
