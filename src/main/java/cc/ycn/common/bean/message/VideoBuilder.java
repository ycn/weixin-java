package cc.ycn.common.bean.message;

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
}
