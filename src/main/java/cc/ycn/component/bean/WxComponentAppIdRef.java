package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
public class WxComponentAppIdRef implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;


    public WxComponentAppIdRef() {

    }

    public WxComponentAppIdRef(String componentAppId) {
        this.componentAppId = componentAppId;
    }


    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }
}
