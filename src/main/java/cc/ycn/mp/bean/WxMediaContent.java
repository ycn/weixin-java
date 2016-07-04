package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydz on 16/7/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxMediaContent implements Serializable {

    @JSONField(name = "news_item")
    private List<WxMediaNewsItem> newsItems;

    public WxMediaContent() {
    }

    public List<WxMediaNewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<WxMediaNewsItem> newsItems) {
        this.newsItems = newsItems;
    }
}
