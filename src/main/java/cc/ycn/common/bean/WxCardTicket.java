package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/17/15.
 */
public class WxCardTicket implements Serializable {

    private String ticket;

    @JSONField(name = "expires_in")
    private long expiresIn;

    public WxCardTicket() {

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
