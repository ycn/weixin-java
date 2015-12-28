package cc.ycn.cp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy on 12/14/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxDepartmentList implements Serializable {

    @JSONField(name = "department")
    private List<WxDepartment> departments;

    public WxDepartmentList() {

    }

    public List<WxDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(List<WxDepartment> departments) {
        this.departments = departments;
    }
}
