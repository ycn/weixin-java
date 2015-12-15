package cc.ycn.cp.bean;

import java.io.Serializable;

/**
 * Created by andy on 12/14/15.
 */
public class WxDepartment implements Serializable {

    private long id;
    private String name;
    private long parentid;
    private long order;

    public WxDepartment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentid() {
        return parentid;
    }

    public void setParentid(long parentid) {
        this.parentid = parentid;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }
}
