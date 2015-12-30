package cc.ycn.common.bean.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/30/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAppMessage implements Serializable {

    private String appId;

    private WxMessage message;

    public WxAppMessage() {

    }

    public WxAppMessage(String appId, WxMessage message) {
        this.appId = appId;
        this.message = message;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public WxMessage getMessage() {
        return message;
    }

    public void setMessage(WxMessage message) {
        this.message = message;
    }
}
