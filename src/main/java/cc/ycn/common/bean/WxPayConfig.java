package cc.ycn.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 微信支付配置
 *
 * @author andy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxPayConfig implements Serializable {

    private String appid;
    private String secret;
    private String mchid;
    private String comAppid; // 消息服务商appid

    public WxPayConfig() {

    }

    public WxPayConfig(String appid) {
        this.appid = appid;
    }

    // 支付是否为代理模式
    public boolean isAuthorizer() {
        return comAppid != null && !comAppid.isEmpty();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getComAppid() {
        return comAppid;
    }

    public void setComAppid(String comAppid) {
        this.comAppid = comAppid;
    }
}
