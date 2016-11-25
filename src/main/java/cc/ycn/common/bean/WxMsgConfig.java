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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WxMsgConfig implements Serializable {

    private WxApiType type;
    private String appid;
    private String secret;
    private String token;
    private String aesKey;
    private String salt;
    private String comAppid; // 消息服务商appid

    public WxMsgConfig() {

    }

    public WxMsgConfig(String appid, WxApiType type) {
        this.appid = appid;
        this.type = type;
    }

    // 消息是否为代理模式
    public boolean isAuthorizer() {
        return comAppid != null && !comAppid.isEmpty();
    }

    public WxApiType getType() {
        return type;
    }

    public void setType(WxApiType type) {
        this.type = type;
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

    public String getComAppid() {
        return comAppid;
    }

    public void setComAppid(String comAppid) {
        this.comAppid = comAppid;
    }

}
