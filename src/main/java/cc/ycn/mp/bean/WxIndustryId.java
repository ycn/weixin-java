package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxIndustryId implements Serializable {

    @JSONField(name = "industry_id1")
    private String industryId1;

    @JSONField(name = "industry_id2")
    private String industryId2;

    public WxIndustryId() {

    }


    public String getIndustryId1() {
        return industryId1;
    }

    public void setIndustryId1(String industryId1) {
        this.industryId1 = industryId1;
    }

    public String getIndustryId2() {
        return industryId2;
    }

    public void setIndustryId2(String industryId2) {
        this.industryId2 = industryId2;
    }
}
