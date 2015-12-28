package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAuthorizerInfo implements Serializable {

    /**
     * 授权方昵称
     */
    @JSONField(name = "nick_name")
    private String nickName;

    /**
     * 授权方头像
     */
    @JSONField(name = "head_img")
    private String headImg;

    /**
     * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    @JSONField(name = "service_type_info")
    private WxServiceTypeInfo serviceTypeInfo;

    /**
     * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    @JSONField(name = "verify_type_info")
    private WxVerifyTypeInfo verifyTypeInfo;

    /**
     * 授权方公众号的原始ID
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     * 授权方公众号所设置的微信号，可能为空
     */
    private String alias;

    /**
     * 功能的开通状况
     */
    @JSONField(name = "business_info")
    private WxBusinessInfo businessInfo;

    /**
     * 二维码图片的URL
     */
    @JSONField(name = "qrcode_url")
    private String qrcodeUrl;


    /**
     * 授权信息
     */
    @JSONField(name = "authorization_info")
    private WxAuthorizationInfo authorizationInfo;


    public WxAuthorizerInfo() {

    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public WxServiceTypeInfo getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public void setServiceTypeInfo(WxServiceTypeInfo serviceTypeInfo) {
        this.serviceTypeInfo = serviceTypeInfo;
    }

    public WxVerifyTypeInfo getVerifyTypeInfo() {
        return verifyTypeInfo;
    }

    public void setVerifyTypeInfo(WxVerifyTypeInfo verifyTypeInfo) {
        this.verifyTypeInfo = verifyTypeInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public WxBusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(WxBusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public WxAuthorizationInfo getAuthorizationInfo() {
        return authorizationInfo;
    }

    public void setAuthorizationInfo(WxAuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }
}
