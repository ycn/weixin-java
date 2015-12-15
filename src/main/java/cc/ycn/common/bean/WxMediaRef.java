package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/15/15.
 */
public class WxMediaRef implements Serializable {

    @JSONField(name = "media_id")
    private String mediaId;

    public WxMediaRef() {

    }

    public WxMediaRef(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
