package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxShortTemplateIdRef implements Serializable {

    @JSONField(name = "template_id_short")
    private String templateIdShort;

    public WxShortTemplateIdRef() {

    }

    public WxShortTemplateIdRef(String templateIdShort) {
        this.templateIdShort = templateIdShort;
    }

    public String getTemplateIdShort() {
        return templateIdShort;
    }

    public void setTemplateIdShort(String templateIdShort) {
        this.templateIdShort = templateIdShort;
    }
}
