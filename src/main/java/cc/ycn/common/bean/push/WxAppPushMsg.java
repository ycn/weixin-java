package cc.ycn.common.bean.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/28/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAppPushMsg implements Serializable {

    private String appId;

    private WxPushMsg pushMsg;

    public WxAppPushMsg() {

    }

    public WxAppPushMsg(String appId, WxPushMsg pushMsg) {
        this.appId = appId;
        this.pushMsg = pushMsg;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public WxPushMsg getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(WxPushMsg pushMsg) {
        this.pushMsg = pushMsg;
    }
}
