package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
public class WxcardBuilder extends BaseBuilder<WxcardBuilder> {

    public WxcardBuilder() {
        message.wxCard = message.new WxMsgWxcard();
    }

    public WxcardBuilder cardId(String cardId) {
        if (!isEmpty(cardId))
            message.wxCard.cardId = cardId;
        return this;
    }

    public WxcardBuilder cardExt(String cardExt) {
        if (!isEmpty(cardExt))
            message.wxCard.cardExt = cardExt;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.WXCARD;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.wxCard.cardId))
            throw new WxErrorException(new WxError(1004, "missing cardId"));
        if (isEmpty(message.wxCard.cardExt))
            throw new WxErrorException(new WxError(1004, "missing cardExt"));
    }
}
