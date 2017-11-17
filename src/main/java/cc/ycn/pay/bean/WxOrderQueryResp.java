package cc.ycn.pay.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by andy on 12/31/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxOrderQueryResp extends WxPayBaseResp {

    /* protected */
    protected String openid;
    protected String is_subscribe;
    protected String trade_type;
    protected String trade_state;
    protected String bank_type;
    protected int total_fee;
    protected String fee_type;
    protected int cash_fee;
    protected String cash_fee_type;
    protected int coupon_fee;
    protected int coupon_count;
    protected int coupon_batch_id_0;
    protected int coupon_batch_id_1;
    protected int coupon_batch_id_2;
    protected int coupon_batch_id_3;
    protected int coupon_batch_id_4;
    protected String coupon_id_0;
    protected String coupon_id_1;
    protected String coupon_id_2;
    protected String coupon_id_3;
    protected String coupon_id_4;
    protected int coupon_fee_0;
    protected int coupon_fee_1;
    protected int coupon_fee_2;
    protected int coupon_fee_3;
    protected int coupon_fee_4;

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    protected String transaction_id;
    protected String out_trade_no;
    protected String attach;
    protected String time_end;
    protected String trade_state_desc;
    protected String sub_openid;


    public WxOrderQueryResp() {

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

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public int getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(int cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public int getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(int coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public int getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(int coupon_count) {
        this.coupon_count = coupon_count;
    }

    public int getCoupon_batch_id_0() {
        return coupon_batch_id_0;
    }

    public void setCoupon_batch_id_0(int coupon_batch_id_0) {
        this.coupon_batch_id_0 = coupon_batch_id_0;
    }

    public int getCoupon_batch_id_1() {
        return coupon_batch_id_1;
    }

    public void setCoupon_batch_id_1(int coupon_batch_id_1) {
        this.coupon_batch_id_1 = coupon_batch_id_1;
    }

    public int getCoupon_batch_id_2() {
        return coupon_batch_id_2;
    }

    public void setCoupon_batch_id_2(int coupon_batch_id_2) {
        this.coupon_batch_id_2 = coupon_batch_id_2;
    }

    public int getCoupon_batch_id_3() {
        return coupon_batch_id_3;
    }

    public void setCoupon_batch_id_3(int coupon_batch_id_3) {
        this.coupon_batch_id_3 = coupon_batch_id_3;
    }

    public int getCoupon_batch_id_4() {
        return coupon_batch_id_4;
    }

    public void setCoupon_batch_id_4(int coupon_batch_id_4) {
        this.coupon_batch_id_4 = coupon_batch_id_4;
    }

    public String getCoupon_id_0() {
        return coupon_id_0;
    }

    public void setCoupon_id_0(String coupon_id_0) {
        this.coupon_id_0 = coupon_id_0;
    }

    public String getCoupon_id_1() {
        return coupon_id_1;
    }

    public void setCoupon_id_1(String coupon_id_1) {
        this.coupon_id_1 = coupon_id_1;
    }

    public String getCoupon_id_2() {
        return coupon_id_2;
    }

    public void setCoupon_id_2(String coupon_id_2) {
        this.coupon_id_2 = coupon_id_2;
    }

    public String getCoupon_id_3() {
        return coupon_id_3;
    }

    public void setCoupon_id_3(String coupon_id_3) {
        this.coupon_id_3 = coupon_id_3;
    }

    public String getCoupon_id_4() {
        return coupon_id_4;
    }

    public void setCoupon_id_4(String coupon_id_4) {
        this.coupon_id_4 = coupon_id_4;
    }

    public int getCoupon_fee_0() {
        return coupon_fee_0;
    }

    public void setCoupon_fee_0(int coupon_fee_0) {
        this.coupon_fee_0 = coupon_fee_0;
    }

    public int getCoupon_fee_1() {
        return coupon_fee_1;
    }

    public void setCoupon_fee_1(int coupon_fee_1) {
        this.coupon_fee_1 = coupon_fee_1;
    }

    public int getCoupon_fee_2() {
        return coupon_fee_2;
    }

    public void setCoupon_fee_2(int coupon_fee_2) {
        this.coupon_fee_2 = coupon_fee_2;
    }

    public int getCoupon_fee_3() {
        return coupon_fee_3;
    }

    public void setCoupon_fee_3(int coupon_fee_3) {
        this.coupon_fee_3 = coupon_fee_3;
    }

    public int getCoupon_fee_4() {
        return coupon_fee_4;
    }

    public void setCoupon_fee_4(int coupon_fee_4) {
        this.coupon_fee_4 = coupon_fee_4;
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

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }


}
