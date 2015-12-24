package cc.ycn.mp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxMsgIdRef implements Serializable {

    @JSONField(name = "msgid")
    private String msgId;

    public WxMsgIdRef() {

    }


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
