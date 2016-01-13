package cc.ycn.pay.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by andy on 12/31/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxRefundQueryResp extends WxPayBaseResp {

    /* private */
    protected String transaction_id;

    protected String out_trade_no;

    protected Integer total_fee;

    protected String fee_type;

    protected Integer cash_fee;

    protected Integer refund_count;

    protected String out_refund_no_0;
    protected String out_refund_no_1;
    protected String out_refund_no_2;
    protected String out_refund_no_3;
    protected String out_refund_no_4;

    protected String refund_id_0;
    protected String refund_id_1;
    protected String refund_id_2;
    protected String refund_id_3;
    protected String refund_id_4;

    protected String refund_channel_0;
    protected String refund_channel_1;
    protected String refund_channel_2;
    protected String refund_channel_3;
    protected String refund_channel_4;

    protected Integer refund_fee_0;
    protected Integer refund_fee_1;
    protected Integer refund_fee_2;
    protected Integer refund_fee_3;
    protected Integer refund_fee_4;

    protected Integer coupon_refund_fee_0;
    protected Integer coupon_refund_fee_1;
    protected Integer coupon_refund_fee_2;
    protected Integer coupon_refund_fee_3;
    protected Integer coupon_refund_fee_4;

    protected Integer coupon_refund_count_0;
    protected Integer coupon_refund_count_1;
    protected Integer coupon_refund_count_2;
    protected Integer coupon_refund_count_3;
    protected Integer coupon_refund_count_4;

    protected String coupon_refund_batch_id_0_0;
    protected String coupon_refund_batch_id_0_1;
    protected String coupon_refund_batch_id_0_2;
    protected String coupon_refund_batch_id_0_3;
    protected String coupon_refund_batch_id_0_4;

    protected String coupon_refund_batch_id_1_0;
    protected String coupon_refund_batch_id_1_1;
    protected String coupon_refund_batch_id_1_2;
    protected String coupon_refund_batch_id_1_3;
    protected String coupon_refund_batch_id_1_4;

    protected String coupon_refund_batch_id_2_0;
    protected String coupon_refund_batch_id_2_1;
    protected String coupon_refund_batch_id_2_2;
    protected String coupon_refund_batch_id_2_3;
    protected String coupon_refund_batch_id_2_4;

    protected String coupon_refund_batch_id_3_0;
    protected String coupon_refund_batch_id_3_1;
    protected String coupon_refund_batch_id_3_2;
    protected String coupon_refund_batch_id_3_3;
    protected String coupon_refund_batch_id_3_4;

    protected String coupon_refund_batch_id_4_0;
    protected String coupon_refund_batch_id_4_1;
    protected String coupon_refund_batch_id_4_2;
    protected String coupon_refund_batch_id_4_3;
    protected String coupon_refund_batch_id_4_4;

    protected String coupon_refund_id_0_0;
    protected String coupon_refund_id_0_1;
    protected String coupon_refund_id_0_2;
    protected String coupon_refund_id_0_3;
    protected String coupon_refund_id_0_4;

    protected String coupon_refund_id_1_0;
    protected String coupon_refund_id_1_1;
    protected String coupon_refund_id_1_2;
    protected String coupon_refund_id_1_3;
    protected String coupon_refund_id_1_4;

    protected String coupon_refund_id_2_0;
    protected String coupon_refund_id_2_1;
    protected String coupon_refund_id_2_2;
    protected String coupon_refund_id_2_3;
    protected String coupon_refund_id_2_4;

    protected String coupon_refund_id_3_0;
    protected String coupon_refund_id_3_1;
    protected String coupon_refund_id_3_2;
    protected String coupon_refund_id_3_3;
    protected String coupon_refund_id_3_4;

    protected String coupon_refund_id_4_0;
    protected String coupon_refund_id_4_1;
    protected String coupon_refund_id_4_2;
    protected String coupon_refund_id_4_3;
    protected String coupon_refund_id_4_4;

    protected Integer coupon_refund_fee_0_0;
    protected Integer coupon_refund_fee_0_1;
    protected Integer coupon_refund_fee_0_2;
    protected Integer coupon_refund_fee_0_3;
    protected Integer coupon_refund_fee_0_4;

    protected Integer coupon_refund_fee_1_0;
    protected Integer coupon_refund_fee_1_1;
    protected Integer coupon_refund_fee_1_2;
    protected Integer coupon_refund_fee_1_3;
    protected Integer coupon_refund_fee_1_4;

    protected Integer coupon_refund_fee_2_0;
    protected Integer coupon_refund_fee_2_1;
    protected Integer coupon_refund_fee_2_2;
    protected Integer coupon_refund_fee_2_3;
    protected Integer coupon_refund_fee_2_4;

    protected Integer coupon_refund_fee_3_0;
    protected Integer coupon_refund_fee_3_1;
    protected Integer coupon_refund_fee_3_2;
    protected Integer coupon_refund_fee_3_3;
    protected Integer coupon_refund_fee_3_4;

    protected Integer coupon_refund_fee_4_0;
    protected Integer coupon_refund_fee_4_1;
    protected Integer coupon_refund_fee_4_2;
    protected Integer coupon_refund_fee_4_3;
    protected Integer coupon_refund_fee_4_4;

    protected String refund_status_0;
    protected String refund_status_1;
    protected String refund_status_2;
    protected String refund_status_3;
    protected String refund_status_4;

    protected String refund_recv_accout_0;
    protected String refund_recv_accout_1;
    protected String refund_recv_accout_2;
    protected String refund_recv_accout_3;
    protected String refund_recv_accout_4;

}
