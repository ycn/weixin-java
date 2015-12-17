package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
public class WxTicket implements Serializable {

    private String ticket;

    private String url;

    @JSONField(name = "expire_seconds")
    private long expireSeconds;

    
    public WxTicket() {

    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
