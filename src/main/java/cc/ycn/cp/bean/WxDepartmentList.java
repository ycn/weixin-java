package cc.ycn.cp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy on 12/14/15.
 */
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
