package cc.ycn.component.bean.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Created by andy on 12/22/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxComponentAuthPushMsg implements Serializable {

    @JacksonXmlProperty(localName = "AppId")
    private String appId;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime; // seconds

    @JacksonXmlProperty(localName = "InfoType")
    private String infoType;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "ComponentVerifyTicket")
    private String componentVerifyTicket;

    @JacksonXmlProperty(localName = "AuthorizerAppid")
    private String authorizerAppId;

    public WxComponentAuthPushMsg() {

    }

    public String getUniqId() {
        String prefix = appId == null ? "" : appId + ":";
        return prefix + authorizerAppId + ":" + createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getComponentVerifyTicket() {
        return componentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        this.componentVerifyTicket = componentVerifyTicket;
    }

    public String getAuthorizerAppId() {
        return authorizerAppId;
    }

    public void setAuthorizerAppId(String authorizerAppId) {
        this.authorizerAppId = authorizerAppId;
    }
}
