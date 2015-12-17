package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
public class WxScanScene implements Serializable {

    @JSONField(name = "action_name")
    private String actionName;

    private WxScanSceneId scene;

    @JSONField(name = "expire_seconds")
    private long expireSeconds;


    public WxScanScene() {

    }


    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(WxQRType wxQRType) {
        this.actionName = wxQRType.name();
    }

    public WxScanSceneId getScene() {
        return scene;
    }

    public void setScene(WxScanSceneId scene) {
        this.scene = scene;
    }
}
