package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/15/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseBuilder<T> {

    protected WxMessage message = new WxMessage();

    abstract protected WxMsgType getType();

    public T toUser(String toUser) {
        if (!isEmpty(toUser))
            message.toUser = toUser;
        return (T) this;
    }

    public T toParty(String toParty) {
        if (!isEmpty(toParty))
            message.toParty = toParty;
        return (T) this;
    }

    public T toTag(String toTag) {
        if (!isEmpty(toTag))
            message.toTag = toTag;
        return (T) this;
    }

    public T agentId(String agentId) {
        if (!isEmpty(agentId))
            message.agentId = agentId;
        return (T) this;
    }

    public T kfAccount(String kfAccount) {
        if (!isEmpty(kfAccount))
            message.customService = message.new WxMsgKf(kfAccount);
        return (T) this;
    }

    public WxMessage build() throws WxErrorException {
        WxMsgType type = getType();
        if (type == null)
            throw new WxErrorException(new WxError(1004, "missing msgType"));

        if (isEmpty(message.toUser) && isEmpty(message.toParty) && isEmpty(message.toTag))
            throw new WxErrorException(new WxError(1004, "missing toUser/toParty/toTag"));


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
