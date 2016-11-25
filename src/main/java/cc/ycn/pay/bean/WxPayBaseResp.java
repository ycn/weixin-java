package cc.ycn.pay.bean;

import cc.ycn.common.WeixinSignTool;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.bean.WxPayConfig;

import java.io.Serializable;

/**
 * Created by andy on 1/4/16.
 */
public abstract class WxPayBaseResp implements Serializable {

    /* common */
    protected String return_code;

    protected String return_msg;

    protected String appid;

    protected String mch_id;

    protected String sub_appid;

    protected String sub_mch_id;

    protected String device_info;

    protected String nonce_str;

    protected String sign;

    protected String result_code;

    protected String err_code;

    protected String err_code_des;

    public WxPayBaseResp() {

    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
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

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public boolean isValid(WxMsgConfig config, WxPayConfig payConfig) {
        if (config == null) return false;
        String sign = WeixinSignTool.createPaySignature(config, payConfig, this, new String[]{"sign"});
        return sign.equals(this.sign);
    }
}
