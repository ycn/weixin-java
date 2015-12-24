package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxTemplateIdRef implements Serializable {

    @JSONField(name = "template_id")
    private String templateId;

    public WxTemplateIdRef() {

    }

    public WxTemplateIdRef(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
