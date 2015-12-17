package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
public class VideoBuilder extends BaseBuilder<VideoBuilder> {

    public VideoBuilder() {
        message.video = message.new WxMsgVideo();
    }

    public VideoBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            message.video.mediaId = mediaId;
        return this;
    }

    public VideoBuilder thumbMediaId(String thumbMediaId) {
        if (!isEmpty(thumbMediaId))
            message.video.thumbMediaId = thumbMediaId;
        return this;
    }

    public VideoBuilder title(String title) {
        if (!isEmpty(title))
            message.video.title = title;
        return this;
    }

    public VideoBuilder description(String description) {
        if (!isEmpty(description))
            message.video.description = description;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.VIDEO;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.video.mediaId))
            throw new WxErrorException(new WxError(1004, "missing mediaId"));
        if (isEmpty(message.video.thumbMediaId))
            throw new WxErrorException(new WxError(1004, "missing thumbMediaId"));
        if (isEmpty(message.video.title))
            throw new WxErrorException(new WxError(1004, "missing title"));
        if (isEmpty(message.video.description))
            throw new WxErrorException(new WxError(1004, "missing description"));
    }
}
