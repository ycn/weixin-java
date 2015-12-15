package cc.ycn.common.bean.message;

/**
 * Created by andy on 12/15/15.
 */
public class VoiceBuilder extends BaseBuilder<VoiceBuilder> {

    public VoiceBuilder() {
        message.voice = message.new WxMsgVoice();
    }

    public VoiceBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            message.voice.mediaId = mediaId;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.VOICE;
    }
}
