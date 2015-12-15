package cc.ycn.common.bean.message;

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
}
