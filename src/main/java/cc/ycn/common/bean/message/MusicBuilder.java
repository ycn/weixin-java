package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
public class MusicBuilder extends BaseBuilder<MusicBuilder> {

    public MusicBuilder() {
        message.music = message.new WxMsgMusic();
    }

    public MusicBuilder musicUrl(String musicUrl) {
        if (!isEmpty(musicUrl))
            message.music.musicUrl = musicUrl;
        return this;
    }

    public MusicBuilder hqMusicUrl(String hqMusicUrl) {
        if (!isEmpty(hqMusicUrl))
            message.music.hqMusicUrl = hqMusicUrl;
        return this;
    }

    public MusicBuilder thumbMediaId(String thumbMediaId) {
        if (!isEmpty(thumbMediaId))
            message.music.thumbMediaId = thumbMediaId;
        return this;
    }

    public MusicBuilder title(String title) {
        if (!isEmpty(title))
            message.music.title = title;
        return this;
    }

    public MusicBuilder description(String description) {
        if (!isEmpty(description))
            message.music.description = description;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.MUSIC;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.music.title))
            throw new WxErrorException(new WxError(1004, "missing title"));
        if (isEmpty(message.music.description))
            throw new WxErrorException(new WxError(1004, "missing description"));
        if (isEmpty(message.music.thumbMediaId))
            throw new WxErrorException(new WxError(1004, "missing thumbMediaId"));
        if (isEmpty(message.music.musicUrl))
            throw new WxErrorException(new WxError(1004, "missing musicUrl"));
        if (isEmpty(message.music.hqMusicUrl))
            throw new WxErrorException(new WxError(1004, "missing hqMusicUrl"));
    }
}
