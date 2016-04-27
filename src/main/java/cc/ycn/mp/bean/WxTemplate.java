package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ydz on 16/4/23.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxTemplate implements Serializable {

    @JSONField(name = "template_id")
    private String templateId;

    @JSONField(name = "title")
    private String title;


    public WxTemplate() {

    }

    public WxTemplate(String templateId, String title) {
        this.templateId = templateId;
        this.title = title;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
