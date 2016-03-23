package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxScanSceneId implements Serializable {

    @JSONField(name = "scene_id")
    private Long sceneId;

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

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }
}
