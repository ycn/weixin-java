package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ydz on 16/5/9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxConnectUrl implements Serializable {

    @JSONField(name = "errcode")
    private String errorCode;

    @JSONField(name = "data")
    private WxConnectUrlContent wxConnectUrlContent;


    public WxConnectUrlContent getWxConnectUrlContent() {
        return wxConnectUrlContent;
    }

    public void setWxConnectUrlContent(WxConnectUrlContent wxConnectUrlContent) {
        this.wxConnectUrlContent = wxConnectUrlContent;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}