package cc.ycn.mp.bean;

import java.io.Serializable;

/**
 * Created by andy on 12/26/15.
 */
public class WxScanActionInfo implements Serializable {

    private WxScanSceneId scene;

    public WxScanActionInfo() {

    }

    public WxScanActionInfo(long id) {
        this.scene = new WxScanSceneId(id);
    }

    public WxScanActionInfo(String id) {
        this.scene = new WxScanSceneId(id);
    }

    public WxScanSceneId getScene() {
        return scene;
    }

    public void setScene(WxScanSceneId scene) {
        this.scene = scene;
    }
}
