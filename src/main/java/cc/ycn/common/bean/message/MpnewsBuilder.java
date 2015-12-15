package cc.ycn.common.bean.message;

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
}
