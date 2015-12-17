package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
public class ImageBuilder extends BaseBuilder<ImageBuilder> {

    public ImageBuilder() {
        message.image = message.new WxMsgImage();
    }

    public ImageBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            message.image.mediaId = mediaId;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.IMAGE;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.image.mediaId))
            throw new WxErrorException(new WxError(1004, "missing mediaId"));
    }
}
