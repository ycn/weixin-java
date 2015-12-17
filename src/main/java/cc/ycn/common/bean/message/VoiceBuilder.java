package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

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

    @Override
    protected void isValid() {
        if (isEmpty(message.voice.mediaId))
            throw new WxErrorException(new WxError(1004, "missing mediaId"));
    }
}
