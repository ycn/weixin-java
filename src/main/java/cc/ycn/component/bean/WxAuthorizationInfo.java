package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxAuthorizationInfo implements Serializable {

    @JSONField(name = "appid")
    private String appId;

    public WxAuthorizationInfo() {

    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
