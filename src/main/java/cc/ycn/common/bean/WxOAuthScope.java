package cc.ycn.common.bean;

/**
 * Created by andy on 12/15/15.
 */
public enum WxOAuthScope {

    BASE("snsapi_base"),
    USER_INFO("snsapi_userinfo"),
    WEB("snsapi_login");

    private String info;

    private WxOAuthScope(String info) {
        this.info = info;
    }

    public String info() {
        return info;
    }

}
