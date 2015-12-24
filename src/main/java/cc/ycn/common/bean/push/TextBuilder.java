package cc.ycn.common.bean.push;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.message.WxMsgType;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/24/15.
 */
public class TextBuilder extends BaseBuilder<TextBuilder> {

    public TextBuilder() {
    }

    public TextBuilder content(String content) {
        if (!isEmpty(content))
            message.content = content;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.TEXT;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.content))
            throw new WxErrorException(new WxError(1004, "missing content"));
    }
}
