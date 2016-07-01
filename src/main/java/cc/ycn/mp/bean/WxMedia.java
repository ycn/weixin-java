package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydz on 16/6/25.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxMedia implements Serializable {
    @JSONField(name = "total_count")
    private int totalCount;

    @JSONField(name = "item_count")
    private int itemCount;

    @JSONField(name = "item")
    private List<MediaItem> mediaItemList;

    public WxMedia() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<MediaItem> getMediaItemList() {
        return mediaItemList;
    }

    public void setMediaItemList(List<MediaItem> mediaItemList) {
        this.mediaItemList = mediaItemList;
    }
}
