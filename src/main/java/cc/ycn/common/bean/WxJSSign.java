package cc.ycn.common.bean;

import cc.ycn.common.util.WeixinSignTool;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by andy on 12/17/15.
 */
public class WxJSSign implements Serializable {

    @JSONField(name = "appid")
    private String appId;

    @JSONField(name = "noncestr")
    private String nonceStr;

    @JSONField(name = "timestamp")
    private String timeStamp;

    private String url;

    private String signature;

    public WxJSSign() {

    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        // 删除#部分
        if (url.contains("#")) {
            String[] parts = url.split("#");
            url = parts[0];
        }

        this.url = url;

    }

    public String getSignature() {
        return signature;
    }

    public void createSignature(String ticket) {
        if (ticket == null || ticket.isEmpty())
            return;

        String packValue = WeixinSignTool.packValue(Arrays.asList(
                "noncestr=" + nonceStr,
                "jsapi_ticket=" + ticket,
                "timestamp=" + timeStamp,
                "url=" + url
        ), "&");

        this.signature = WeixinSignTool.createJSSignature(packValue);
    }
}
