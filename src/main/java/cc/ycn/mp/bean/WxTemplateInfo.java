package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydz on 16/4/23.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxTemplateInfo implements Serializable {

    @JSONField(name = "template_list")
    private List<WxTemplate> templateList;

    public WxTemplateInfo() {

    }

    public WxTemplateInfo(List<WxTemplate> templateList) {
        this.templateList = templateList;
    }

    public List<WxTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<WxTemplate> templateList) {
        this.templateList = templateList;
    }
}
