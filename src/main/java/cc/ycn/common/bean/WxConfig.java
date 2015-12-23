package cc.ycn.common.bean;

import cc.ycn.common.constant.WxApiType;

import java.io.Serializable;

/**
 * 微信公众号配置
 *
 * @author andy
 */
public final class WxConfig implements Serializable {

    private WxApiType type;
    private String appId;
    private String appSecret;
    private String token;
    private String aesKey;
    private String salt;
    // 有componentAppId为第三方代理模式处理
    private String componentAppId;

    public WxConfig() {

    }

    public WxConfig(String appId, WxApiType type) {
        this.appId = appId;
        this.type = type;
    }

    // 第三方代理模式:授权方
    public boolean isAuthorizer() {
        return componentAppId != null && !componentAppId.isEmpty();
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
}
