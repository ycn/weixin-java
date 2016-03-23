package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Joiner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy on 12/14/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxIpList implements Serializable {

    @JSONField(name = "ip_list")
    private List<String> ipList;

    public WxIpList() {
        
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    @Override
    public String toString() {
        return "WxIpList(ip_list:" + Joiner.on(",").join(ipList) + ")";
    }
}
