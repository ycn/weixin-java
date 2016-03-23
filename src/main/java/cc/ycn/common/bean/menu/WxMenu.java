package cc.ycn.common.bean.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 微信菜单
 *
 * @author andy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxMenu implements Serializable {

    List<WxMenu> button;

    @JSONField(name = "sub_button")
    List<WxMenu> subButton;

    String type;

    String name;

    String key;

    String url;

    @JSONField(name = "media_id")
    String mediaId;

    @JSONField(name = "matchrule")
    WxMenuMatchRule matchRule;

    WxMenu() {

    }

    // 菜单
    public static MenuBuilder MENU() {
        return new MenuBuilder();
    }

    // 点击推事件
    public static ClickBuilder CLICK() {
        return new ClickBuilder();
    }

    // 跳转URL
    public static ViewBuilder VIEW() {
        return new ViewBuilder();
    }

    // 扫码推事件
    public static ScanCodePushBuilder SCANCODE_PUSH() {
        return new ScanCodePushBuilder();
    }

    // 扫码推事件且弹出“消息接收中”提示框
    public static ScanCodeWaitMsgBuilder SCANCODE_WAITMSG() {
        return new ScanCodeWaitMsgBuilder();
    }

    // 弹出系统拍照发图
    public static PicSysPhotoBuilder PIC_SYSPHOTO() {
        return new PicSysPhotoBuilder();
    }

    // 弹出拍照或者相册发图
    public static PicPhotoOrAlbumBuilder PIC_PHOTO_OR_ALBUM() {
        return new PicPhotoOrAlbumBuilder();
    }

    // 弹出微信相册发图器
    public static PicWeixinBuilder PIC_WEIXIN() {
        return new PicWeixinBuilder();
    }

    // 弹出地理位置选择器
    public static LocationSelectBuilder LOCATION_SELECT() {
        return new LocationSelectBuilder();
    }

    // 下发消息（除文本消息）
    public static MediaIdBuilder MEDIA_ID() {
        return new MediaIdBuilder();
    }

    // 跳转图文消息URL
    public static ViewLimitedBuilder VIEW_LIMITED() {
        return new ViewLimitedBuilder();
    }


    public List<WxMenu> getButton() {
        return button;
    }

    public List<WxMenu> getSubButton() {
        return subButton;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public WxMenuMatchRule getMatchRule() {
        return matchRule;
    }
}
