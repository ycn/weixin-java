package cc.ycn.pay.bean.push;

import cc.ycn.pay.bean.WxPayBaseResp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by andy on 1/4/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxPayPushMsg extends WxPayBaseResp {

    /* private */
    protected String openid;

    protected String is_subscribe;

    protected String sub_openid;

    protected String sub_is_subscribe;

    protected String transaction_id;

    protected String out_trade_no;

    protected String attach;

    protected String time_end;

    protected String trade_type;

    protected String bank_type;

    protected Integer total_fee;

    protected String fee_type;

    protected Integer cash_fee;

    protected String cash_fee_type;

    protected Integer coupon_fee;

    protected Integer coupon_count;

    protected String coupon_id_0;

    protected Integer coupon_fee_0;

    protected String coupon_id_1;

    protected Integer coupon_fee_1;

    protected String coupon_id_2;

    protected Integer coupon_fee_2;

    protected String coupon_id_3;

    protected Integer coupon_fee_3;

    protected String coupon_id_4;

    protected Integer coupon_fee_4;

    protected String coupon_id_5;

    protected Integer coupon_fee_5;


    public WxPayPushMsg() {

    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    public String getSub_is_subscribe() {
        return sub_is_subscribe;
    }

    public void setSub_is_subscribe(String sub_is_subscribe) {
        this.sub_is_subscribe = sub_is_subscribe;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public Integer getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(Integer coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public Integer getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(Integer coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getCoupon_id_0() {
        return coupon_id_0;
    }

    public void setCoupon_id_0(String coupon_id_0) {
        this.coupon_id_0 = coupon_id_0;
    }
    public Integer getCoupon_fee_0() {
        return coupon_fee_0;
    }

    public void setCoupon_fee_0(Integer coupon_fee_0) {
        this.coupon_fee_0 = coupon_fee_0;
    }


    public String getCoupon_id_1() {
        return coupon_id_1;
    }

    public void setCoupon_id_1(String coupon_id_1) {
        this.coupon_id_1 = coupon_id_1;
    }

    public Integer getCoupon_fee_1() {
        return coupon_fee_1;
    }

    public void setCoupon_fee_1(Integer coupon_fee_1) {
        this.coupon_fee_1 = coupon_fee_1;
    }

    public String getCoupon_id_2() {
        return coupon_id_2;
    }

    public void setCoupon_id_2(String coupon_id_2) {
        this.coupon_id_2 = coupon_id_2;
    }

    public Integer getCoupon_fee_2() {
        return coupon_fee_2;
    }

    public void setCoupon_fee_2(Integer coupon_fee_2) {
        this.coupon_fee_2 = coupon_fee_2;
    }

    public String getCoupon_id_3() {
        return coupon_id_3;
    }

    public void setCoupon_id_3(String coupon_id_3) {
        this.coupon_id_3 = coupon_id_3;
    }

    public Integer getCoupon_fee_3() {
        return coupon_fee_3;
    }

    public void setCoupon_fee_3(Integer coupon_fee_3) {
        this.coupon_fee_3 = coupon_fee_3;
    }

    public String getCoupon_id_4() {
        return coupon_id_4;
    }

    public void setCoupon_id_4(String coupon_id_4) {
        this.coupon_id_4 = coupon_id_4;
    }

    public Integer getCoupon_fee_4() {
        return coupon_fee_4;
    }

    public void setCoupon_fee_4(Integer coupon_fee_4) {
        this.coupon_fee_4 = coupon_fee_4;
    }

    public String getCoupon_id_5() {
        return coupon_id_5;
    }

    public void setCoupon_id_5(String coupon_id_5) {
        this.coupon_id_5 = coupon_id_5;
    }

    public Integer getCoupon_fee_5() {
        return coupon_fee_5;
    }

    public void setCoupon_fee_5(Integer coupon_fee_5) {
        this.coupon_fee_5 = coupon_fee_5;
    }
}
