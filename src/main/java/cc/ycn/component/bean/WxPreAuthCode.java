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
public class WxPreAuthCode implements Serializable {

    @JSONField(name = "pre_auth_code")
    private String preAuthCode;

    @JSONField(name = "expires_in")
    private long expiresIn; // second

    public WxPreAuthCode() {

    }


    public String getPreAuthCode() {
        return preAuthCode;
    }

    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
