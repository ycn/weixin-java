package cc.ycn.pay.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Created by andy on 12/31/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxUnifiedOrderResp implements Serializable {

    /* common */
    @JacksonXmlProperty(localName = "return_code")
    private String returnCode;

    @JacksonXmlProperty(localName = "return_msg")
    private String returnMsg;

    @JacksonXmlProperty(localName = "appid")
    private String appId;

    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;

    @JacksonXmlProperty(localName = "sub_appid")
    private String subAppId;

    @JacksonXmlProperty(localName = "sub_mch_id")
    private String subMchId;

    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo;

    @JacksonXmlProperty(localName = "nonce_str")
    private String nonceStr;

    private String sign;

    @JacksonXmlProperty(localName = "result_code")
    private String resultCode;

    @JacksonXmlProperty(localName = "err_code")
    private String errCode;

    @JacksonXmlProperty(localName = "err_code_des")
    private String errCodeDes;

    /* private */
    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;

    @JacksonXmlProperty(localName = "prepay_id")
    private String prepayId;

    @JacksonXmlProperty(localName = "code_url")
    private String codeUrl;


    public WxUnifiedOrderResp() {

    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
