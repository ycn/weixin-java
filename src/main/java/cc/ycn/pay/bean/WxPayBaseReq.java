package cc.ycn.pay.bean;

import cc.ycn.common.WeixinSignTool;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.bean.WxPayConfig;

import java.io.Serializable;

/**
 * Created by andy on 1/4/16.
 */
public abstract class WxPayBaseReq implements Serializable {

    /* common */
    protected String appid;

    protected String mch_id;

    protected String sub_appid;

    protected String sub_mch_id;

    protected String device_info;

    protected String nonce_str;

    protected String sign;

    public WxPayBaseReq() {

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(WxMsgConfig config, WxPayConfig payConfig) {
        if (config == null) return;
        this.sign = WeixinSignTool.createPaySignature(config, payConfig, this, new String[]{"sign"});
    }
}
