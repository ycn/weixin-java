package cc.ycn.pay.bean.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Created by andy on 1/4/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxPayPushMsg implements Serializable {

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
    @JacksonXmlProperty(localName = "openid")
    private String openId;

    @JacksonXmlProperty(localName = "is_subscribe")
    private String isSubscribe;

    @JacksonXmlProperty(localName = "sub_openid")
    private String subOpenId;

    @JacksonXmlProperty(localName = "sub_is_subscribe")
    private String subIsSubscribe;

    @JacksonXmlProperty(localName = "transaction_id")
    private String transactionId;

    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;

    private String attach;

    @JacksonXmlProperty(localName = "time_end")
    private String timeEnd;

    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;

    @JacksonXmlProperty(localName = "bank_type")
    private String bankType;

    @JacksonXmlProperty(localName = "total_fee")
    private Integer totalFee;

    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;

    @JacksonXmlProperty(localName = "cash_fee")
    private Integer cashFee;

    @JacksonXmlProperty(localName = "cash_fee_type")
    private String cashFeeType;

    @JacksonXmlProperty(localName = "coupon_fee")
    private Integer couponFee;

    @JacksonXmlProperty(localName = "coupon_count")
    private Integer couponCount;

    @JacksonXmlProperty(localName = "coupon_id_1")
    private String couponId1;

    @JacksonXmlProperty(localName = "coupon_fee_1")
    private Integer couponFee1;

    @JacksonXmlProperty(localName = "coupon_id_2")
    private String couponId2;

    @JacksonXmlProperty(localName = "coupon_fee_2")
    private Integer couponFee2;

    @JacksonXmlProperty(localName = "coupon_id_3")
    private String couponId3;

    @JacksonXmlProperty(localName = "coupon_fee_3")
    private Integer couponFee3;

    @JacksonXmlProperty(localName = "coupon_id_4")
    private String couponId4;

    @JacksonXmlProperty(localName = "coupon_fee_4")
    private Integer couponFee4;

    @JacksonXmlProperty(localName = "coupon_id_5")
    private String couponId5;

    @JacksonXmlProperty(localName = "coupon_fee_5")
    private Integer couponFee5;


    public WxPayPushMsg() {

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
    }

    public String getSubIsSubscribe() {
        return subIsSubscribe;
    }

    public void setSubIsSubscribe(String subIsSubscribe) {
        this.subIsSubscribe = subIsSubscribe;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getCashFee() {
        return cashFee;
    }

    public void setCashFee(Integer cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public Integer getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(Integer couponFee) {
        this.couponFee = couponFee;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponId1() {
        return couponId1;
    }

    public void setCouponId1(String couponId1) {
        this.couponId1 = couponId1;
    }

    public Integer getCouponFee1() {
        return couponFee1;
    }

    public void setCouponFee1(Integer couponFee1) {
        this.couponFee1 = couponFee1;
    }

    public String getCouponId2() {
        return couponId2;
    }

    public void setCouponId2(String couponId2) {
        this.couponId2 = couponId2;
    }

    public Integer getCouponFee2() {
        return couponFee2;
    }

    public void setCouponFee2(Integer couponFee2) {
        this.couponFee2 = couponFee2;
    }

    public String getCouponId3() {
        return couponId3;
    }

    public void setCouponId3(String couponId3) {
        this.couponId3 = couponId3;
    }

    public Integer getCouponFee3() {
        return couponFee3;
    }

    public void setCouponFee3(Integer couponFee3) {
        this.couponFee3 = couponFee3;
    }

    public String getCouponId4() {
        return couponId4;
    }

    public void setCouponId4(String couponId4) {
        this.couponId4 = couponId4;
    }

    public Integer getCouponFee4() {
        return couponFee4;
    }

    public void setCouponFee4(Integer couponFee4) {
        this.couponFee4 = couponFee4;
    }

    public String getCouponId5() {
        return couponId5;
    }

    public void setCouponId5(String couponId5) {
        this.couponId5 = couponId5;
    }

    public Integer getCouponFee5() {
        return couponFee5;
    }

    public void setCouponFee5(Integer couponFee5) {
        this.couponFee5 = couponFee5;
    }
}
