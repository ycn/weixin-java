package cc.ycn.common.constant;

/**
 * Created by andy on 12/24/15.
 */
public enum CacheKeyPrefix {

    ACCESS_TOKEN("AccessToken:"),
    CARD_TICKET("CardTicket:"),
    JS_TICKET("JsTicket:"),
    PRE_AUTHCODE("PreAuthCode:"),
    REFRESH_TOKEN("RefreshToken:"),
    VERIFY_TICKET("VerifyTicket:"),
    MSG_CONFIG("MsgConfig:"),
    PAY_CONFIG("PayConfig:");

    private String prefix;

    private CacheKeyPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }

    public String key(String key) {
        return prefix + key;
    }
}
