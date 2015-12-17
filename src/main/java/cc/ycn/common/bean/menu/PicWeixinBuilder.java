package cc.ycn.common.bean.menu;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/16/15.
 */
public class PicWeixinBuilder extends BaseBuilder<PicWeixinBuilder> {

    public PicWeixinBuilder() {

    }

    public PicWeixinBuilder key(String key) {
        if (!isEmpty(key))
            menu.key = key;
        return this;
    }

    @Override
    protected WxMenuType getType() {
        return WxMenuType.PIC_WEIXIN;
    }

    @Override
    protected void isValid() {
        if (isEmpty(menu.name))
            throw new WxErrorException(new WxError(1004, "missing name"));
        if (isEmpty(menu.key))
            throw new WxErrorException(new WxError(1004, "missing key"));
    }
}
