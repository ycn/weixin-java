package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/17/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxCardTicket implements Serializable {

    private String ticket;

    @JSONField(name = "expires_in")
    private long expiresIn;

    public WxCardTicket() {

    }

    public WxCardTicket(WxToken wxToken) {
        this.ticket = wxToken.getToken();
        this.expiresIn = wxToken.getExpiresIn();
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
