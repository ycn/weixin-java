package cc.ycn.common.constant;

/**
 * Created by andy on 12/11/15.
 */
public enum WxApiType {

    MP("公众号"),
    CP("企业号");

    private String info;

    private WxApiType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
