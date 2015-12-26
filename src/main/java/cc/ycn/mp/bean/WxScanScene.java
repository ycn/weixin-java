package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
public class WxScanScene implements Serializable {

    @JSONField(name = "action_name")
    private String actionName;

    @JSONField(name = "action_info")
    private WxScanActionInfo actionInfo;

    @JSONField(name = "expire_seconds")
    private Long expireSeconds;


    public WxScanScene() {

    }

    /* 创建临时二维码 */
    public WxScanScene(long id, long expireSeconds) {
        setActionName(WxQRType.QR_SCENE);
        this.actionInfo = new WxScanActionInfo(id);
        if (expireSeconds <= 0) expireSeconds = 30 * 86400;
        this.expireSeconds = expireSeconds;
    }

    /* 创建永久二维码 */
    public WxScanScene(long id) {
        setActionName(WxQRType.QR_LIMIT_SCENE);
        this.actionInfo = new WxScanActionInfo(id);
    }

    /* 创建永久二维码(字符串ID) */
    public WxScanScene(String id) {
        setActionName(WxQRType.QR_LIMIT_STR_SCENE);
        this.actionInfo = new WxScanActionInfo(id);
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(WxQRType wxQRType) {
        this.actionName = wxQRType.name();
    }

    public WxScanActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(WxScanActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
