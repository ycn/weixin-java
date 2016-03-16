package cc.ycn.common.bean;

import cc.ycn.common.constant.WxApiType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 微信公众号配置
 *
 * @author andy
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WxConfig implements Serializable {

    private WxApiType type;
    private String appId;
    private String appSecret;
    private String mchId;
    private String payAppId;
    private String payMchId;
    private String paySecret;
    private String token;
    private String aesKey;
    private String salt;
    // 消息代理商appID
    private String componentAppId;
    // 支付代理商appID
    private String componentPayAppId;

    public WxConfig() {

    }

    public WxConfig(String appId, WxApiType type) {
        this.appId = appId;
        this.type = type;
    }

    // 消息是否为代理模式
    public boolean isAuthorizer() {
        return componentAppId != null && !componentAppId.isEmpty();
    }

    // 支付是否为代理模式
    public boolean isPayAuthorizer() {
        return componentPayAppId != null && !componentPayAppId.isEmpty();
    }

    public WxApiType getType() {
        return type;
    }

    public void setType(WxApiType type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getPayComponentAppId() {
        return componentPayAppId;
    }

    public void setPayComponentAppId(String componentPayAppId) {
        this.componentPayAppId = componentPayAppId;
    }

    public String getPaySecret() {
        return paySecret;
    }

    public void setPaySecret(String paySecret) {
        this.paySecret = paySecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPayAppId() {
        return payAppId;
    }

    public void setPayAppId(String payAppId) {
        this.payAppId = payAppId;
    }

    public String getPayMchId() {
        return payMchId;
    }

    public void setPayMchId(String payMchId) {
        this.payMchId = payMchId;
    }
}
