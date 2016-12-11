package cc.ycn.common.constant;

import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.bean.WxPayConfig;

/**
 * Created by andy on 12/24/15.
 */
public enum WxCacheType {

    ACCESS_TOKEN("AccessToken", String.class),
    CARD_TICKET("CardTicket", String.class),
    JS_TICKET("JsTicket", String.class),
    PRE_AUTHCODE("PreAuthCode", String.class),
    REFRESH_TOKEN("RefreshToken", String.class),
    VERIFY_TICKET("VerifyTicket", String.class),
    MSG_CONFIG("MsgConfig", WxMsgConfig.class),
    PAY_CONFIG("PayConfig", WxPayConfig.class);

    private String prefix;
    private Class clazz;

    private WxCacheType(String prefix, Class clazz) {
        this.prefix = prefix;
        this.clazz = clazz;
    }

    public String prefix() {
        return prefix;
    }

    public Class clazz() {
        return clazz;
    }

    public String key(String key) {
        return prefix + ":" + key;
    }

    public boolean contains(String key) {
        return key != null && key.startsWith(prefix);
    }
}
