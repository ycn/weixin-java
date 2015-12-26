package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
public class WxScanSceneId implements Serializable {

    @JSONField(name = "scene_id")
    private long sceneId;

    @JSONField(name = "scene_str")
    private String sceneStr;

    public WxScanSceneId() {

    }

    public WxScanSceneId(long id) {
        this.sceneId = id;
    }

    public WxScanSceneId(String id) {
        this.sceneStr = id;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }
}
