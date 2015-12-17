package cc.ycn.cp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/15/15.
 */
public class WxQyUser implements Serializable {

    @JSONField(name = "UserId")
    private String userId;

    @JSONField(name = "DeviceId")
    private String deviceId;

    @JSONField(name = "OpenId")
    private String openId;

    public WxQyUser() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
