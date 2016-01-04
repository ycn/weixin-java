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
public class WxUnifiedOrderReq implements Serializable {

    /* common */
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

    /* private */
    private String body;

    private String detail;

    private String attach;

    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;

    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;

    @JacksonXmlProperty(localName = "total_fee")
    private Integer totalFee;

    @JacksonXmlProperty(localName = "spbill_create_ip")
    private String spBillCreateIp;

    @JacksonXmlProperty(localName = "time_start")
    private String timeStart;

    @JacksonXmlProperty(localName = "time_expire")
    private String timeExpire;

    @JacksonXmlProperty(localName = "goods_tag")
    private String goodsTag;

    @JacksonXmlProperty(localName = "notify_url")
    private String notifyUrl;

    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;

    @JacksonXmlProperty(localName = "product_id")
    private String productId;

    @JacksonXmlProperty(localName = "limit_pay")
    private String limitPay;

    @JacksonXmlProperty(localName = "openid")
    private String openId;

    @JacksonXmlProperty(localName = "sub_openid")
    private String subOpenId;


    public WxUnifiedOrderReq() {

    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
