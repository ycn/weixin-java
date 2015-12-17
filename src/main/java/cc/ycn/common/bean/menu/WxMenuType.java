package cc.ycn.common.bean.menu;

/**
 * Created by andy on 12/16/15.
 */
public enum WxMenuType {

    MENU(null),
    CLICK("click"),
    VIEW("view"),
    SCANCODE_PUSH("scancode_push"),
    SCANCODE_WAITMSG("scancode_waitmsg"),
    PIC_SYSPHOTO("pic_sysphoto"),
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),
    PIC_WEIXIN("pic_weixin"),
    LOCATION_SELECT("location_select"),
    MEDIA_ID("media_id"),
    VIEW_LIMITED("view_limited");

    private String info;

    private WxMenuType(String info) {
        this.info = info;
    }

    public String info() {
        return info;
    }

}
