package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 微信错误码说明
 * <table summary="">
 * <tr><td>返回码</td><td>说明</td></tr>
 * <tr><td>-1</td><td>系统繁忙，此时请开发者稍候再试</td></tr>
 * <tr><td>0</td><td>请求成功</td></tr>
 * <tr><td>1001</td><td>缺少配置信息</td></tr>
 * <tr><td>1002</td><td>微信接口请求异常</td></tr>
 * <tr><td>1003</td><td>请求参数异常(接收时)</td></tr>
 * <tr><td>1004</td><td>请求参数异常(发送时)</td></tr>
 * </table>
 *
 * @author andy
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WxError implements Serializable {

    private long errcode;

    private String errmsg;

    @JSONField(name = "invaliduser")
    private String invalidUser;

    @JSONField(name = "invalidparty")
    private String invalidParty;

    @JSONField(name = "invalidtag")
    private String invalidTag;

    public WxError() {

    }

    public WxError(long errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public long getErrcode() {
        return errcode;
    }

    public void setErrcode(long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getInvalidUser() {
        return invalidUser;
    }

    public void setInvalidUser(String invalidUser) {
        this.invalidUser = invalidUser;
    }

    public String getInvalidParty() {
        return invalidParty;
    }

    public void setInvalidParty(String invalidParty) {
        this.invalidParty = invalidParty;
    }

    public String getInvalidTag() {
        return invalidTag;
    }

    public void setInvalidTag(String invalidTag) {
        this.invalidTag = invalidTag;
    }

    @Override
    public String toString() {
        return "WxError(errcode:" + errcode + ",errmsg:" + errmsg + ")";
    }
}
