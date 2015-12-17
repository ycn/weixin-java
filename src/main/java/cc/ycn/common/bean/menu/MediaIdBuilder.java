package cc.ycn.common.bean.menu;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/16/15.
 */
public class MediaIdBuilder extends BaseBuilder<MediaIdBuilder> {

    public MediaIdBuilder() {

    }

    public MediaIdBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            menu.mediaId = mediaId;
        return this;
    }

    @Override
    protected WxMenuType getType() {
        return WxMenuType.MEDIA_ID;
    }

    @Override
    protected void isValid() {
        if (isEmpty(menu.name))
            throw new WxErrorException(new WxError(1004, "missing name"));
        if (isEmpty(menu.mediaId))
            throw new WxErrorException(new WxError(1004, "missing mediaId"));
    }
}
