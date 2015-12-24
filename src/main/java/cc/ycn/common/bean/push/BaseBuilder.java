package cc.ycn.common.bean.push;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.message.WxMsgType;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseBuilder<T> {

    protected WxAnswerMsg message = new WxAnswerMsg();

    abstract protected WxMsgType getType();

    abstract protected void isValid();

    public T toUserName(String toUserName) {
        if (!isEmpty(toUserName))
            message.toUserName = toUserName;
        return (T) this;
    }

    public T fromUserName(String fromUserName) {
        if (!isEmpty(fromUserName))
            message.fromUserName = fromUserName;
        return (T) this;
    }

    public T createTime(long createTime) {
        if (createTime > 0)
            message.createTime = createTime;
        return (T) this;
    }

    public WxAnswerMsg build() throws WxErrorException {
        WxMsgType type = getType();
        if (type == null)
            throw new WxErrorException(new WxError(1004, "missing msgType"));

        if (isEmpty(message.toUserName) || isEmpty(message.fromUserName))
            throw new WxErrorException(new WxError(1004, "missing toUserName/fromUserName"));

        if (WxMsgType.NEWS.equals(type)) {
            if (message.item == null || message.item.isEmpty()) {
                message.articleCount = 0;
            } else {
                message.articleCount = message.item.size();
            }
        }

        isValid();

        message.msgType = type.info();
        return message;
    }

    protected boolean isEmpty(String test) {
        test = getString(test);
        return test.isEmpty();
    }

    protected String getString(String test) {
        return test == null ? "" : test.trim();
    }
}
