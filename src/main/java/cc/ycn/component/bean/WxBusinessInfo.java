package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 用以了解以下功能的开通状况（0代表未开通，1代表已开通）
 * <p/>
 * Created by andy on 12/23/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxBusinessInfo implements Serializable {

    /**
     * 是否开通微信门店功能
     */
    @JSONField(name = "open_store")
    private String openStore;

    /**
     * 是否开通微信扫商品功能
     */
    @JSONField(name = "open_scan")
    private String openScan;

    /**
     * 是否开通微信支付功能
     */
    @JSONField(name = "open_pay")
    private String openPay;

    /**
     * 是否开通微信卡券功能
     */
    @JSONField(name = "open_card")
    private String openCard;

    /**
     * 是否开通微信摇一摇功能
     */
    @JSONField(name = "open_shake")
    private String openShake;

    public WxBusinessInfo() {

    }


    public String getOpenStore() {
        return openStore;
    }

    public void setOpenStore(String openStore) {
        this.openStore = openStore;
    }

    public String getOpenScan() {
        return openScan;
    }

    public void setOpenScan(String openScan) {
        this.openScan = openScan;
    }

    public String getOpenPay() {
        return openPay;
    }

    public void setOpenPay(String openPay) {
        this.openPay = openPay;
    }

    public String getOpenCard() {
        return openCard;
    }

    public void setOpenCard(String openCard) {
        this.openCard = openCard;
    }

    public String getOpenShake() {
        return openShake;
    }

    public void setOpenShake(String openShake) {
        this.openShake = openShake;
    }
}
