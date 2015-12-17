package cc.ycn.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/16/15.
 */
public class WxOpenIdRef implements Serializable {

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "userid")
    private String userId;

    @JSONField(name = "agentid")
    private String agentId;

    
    public WxOpenIdRef() {

    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
