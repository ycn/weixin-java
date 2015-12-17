package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
public class TextBuilder extends BaseBuilder<TextBuilder> {

    public TextBuilder() {
        message.text = message.new WxMsgText();
    }

    public TextBuilder content(String content) {
        if (!isEmpty(content))
            message.text.content = content;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.TEXT;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.text.content))
            throw new WxErrorException(new WxError(1004, "missing content"));
    }
}
