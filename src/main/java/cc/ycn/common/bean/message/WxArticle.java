package cc.ycn.common.bean.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/15/15.
 */
public class WxArticle implements Serializable {
    private String title;

    private String description;

    private String url;

    @JSONField(name = "picurl")
    private String picUrl;


    public WxArticle() {

    }

    public String getUrl() {
        return url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
