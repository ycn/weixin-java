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
public class WxRefundResp implements Serializable {

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
    @JacksonXmlProperty(localName = "transaction_id")
    private String transactionId;

    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;

    @JacksonXmlProperty(localName = "out_refund_no")
    private String outRefundNo;

    @JacksonXmlProperty(localName = "refund_id")
    private String refundId;

    @JacksonXmlProperty(localName = "refund_channel")
    private String refundChannel;

    @JacksonXmlProperty(localName = "refund_fee")
    private Integer refundFee;

    @JacksonXmlProperty(localName = "total_fee")
    private Integer totalFee;

    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;

    @JacksonXmlProperty(localName = "cash_fee")
    private Integer cashFee;

    @JacksonXmlProperty(localName = "cash_refund_fee")
    private Integer cashRefundFee;

    @JacksonXmlProperty(localName = "coupon_refund_fee")
    private Integer couponRefundFee;

    @JacksonXmlProperty(localName = "coupon_refund_count")
    private Integer couponRefundCount;

    @JacksonXmlProperty(localName = "coupon_refund_id")
    private String couponRefundId;


    public WxRefundResp() {

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

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(String refundChannel) {
        this.refundChannel = refundChannel;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
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

    public Integer getCashRefundFee() {
        return cashRefundFee;
    }

    public void setCashRefundFee(Integer cashRefundFee) {
        this.cashRefundFee = cashRefundFee;
    }

    public Integer getCouponRefundFee() {
        return couponRefundFee;
    }

    public void setCouponRefundFee(Integer couponRefundFee) {
        this.couponRefundFee = couponRefundFee;
    }

    public Integer getCouponRefundCount() {
        return couponRefundCount;
    }

    public void setCouponRefundCount(Integer couponRefundCount) {
        this.couponRefundCount = couponRefundCount;
    }

    public String getCouponRefundId() {
        return couponRefundId;
    }

    public void setCouponRefundId(String couponRefundId) {
        this.couponRefundId = couponRefundId;
    }
}
