package cc.ycn.common.bean;

import cc.ycn.common.util.WeixinSignTool;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by andy on 12/17/15.
 */
public class WxCardExt implements Serializable {

    private String code;

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "timestamp")
    private String timeStamp;

    @JSONField(name = "nonce_str")
    private String nonceStr;

    private String signature;

    @JSONField(name = "outer_id")
    private Integer outerId;

    public WxCardExt() {

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getSignature() {
        return signature;
    }

    public void createSignature(String cardId, String ticket) {
        if (cardId == null || cardId.isEmpty())
            return;

        if (ticket == null || ticket.isEmpty())
            return;

        String packValue = WeixinSignTool.packValue(Arrays.asList(
                ticket,
                timeStamp,
                cardId,
                code,
                openId,
                nonceStr
        ), "");

        System.out.println(packValue);

        this.signature = WeixinSignTool.createCardSignature(packValue);
    }
    
}
