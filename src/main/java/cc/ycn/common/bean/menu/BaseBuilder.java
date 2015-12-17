package cc.ycn.common.bean.menu;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/16/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseBuilder<T> {

    protected WxMenu menu = new WxMenu();

    abstract protected WxMenuType getType();

    abstract protected void isValid();

    public T name(String name) {
        if (!isEmpty(name))
            menu.name = name;
        return (T) this;
    }

    public WxMenu build() throws WxErrorException {

        WxMenuType type = getType();
        if (type == null)
            throw new WxErrorException(new WxError(1004, "missing type"));

        menu.type = type.info();

        isValid();

        return menu;
    }

    protected boolean isEmpty(String test) {
        test = getString(test);
        return test.isEmpty();
    }

    protected String getString(String test) {
        return test == null ? "" : test.trim();
    }
}
