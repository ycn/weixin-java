package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/17/15.
 */
public class WxShortUrl implements Serializable {

    @JSONField(name = "short_url")
    private String shortUrl;

    public WxShortUrl() {

    }
    
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
