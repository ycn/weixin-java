package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
class MpnewsBuilder extends BaseBuilder<MpnewsBuilder> {

    public MpnewsBuilder() {
        message.mpNews = message.new WxMsgMpnews();
    }

    public MpnewsBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            message.mpNews.mediaId = mediaId;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.MPNEWS;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.mpNews.mediaId))
            throw new WxErrorException(new WxError(1004, "missing mediaId"));
    }
}
