package cc.ycn.common.bean.message;

/**
 * Created by andy on 12/15/15.
 */
public enum WxMsgType {

    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    FILE("file"),
    MUSIC("music"),
    NEWS("news"),
    MPNEWS("mpnews"),
    WXCARD("wxcard"),
    TEMPLATE(null);

    private String info;

    private WxMsgType(String info) {
        this.info = info;
    }

    public String info() {
        return info;
    }
}
