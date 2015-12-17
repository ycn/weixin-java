package cc.ycn.mp.bean;

/**
 * Created by andy on 12/16/15.
 */
public enum WxQRType {

    QR_SCENE("临时二维码"),
    QR_LIMIT_SCENE("永久二维码"),
    QR_LIMIT_STR_SCENE("永久二维码字符串ID");

    private String info;

    private WxQRType(String info) {
        this.info = info;
    }

    public String info() {
        return info;
    }

}
