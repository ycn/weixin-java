package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
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
