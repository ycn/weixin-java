package cc.ycn.common.bean.menu;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

/**
 * Created by andy on 12/16/15.
 */
public class ViewBuilder extends BaseBuilder<ViewBuilder> {

    public ViewBuilder() {
    }

    public ViewBuilder url(String url) {
        if (!isEmpty(url))
            menu.url = url;
        return this;
    }

    @Override
    protected WxMenuType getType() {
        return WxMenuType.VIEW;
    }

    @Override
    protected void isValid() {
        if (isEmpty(menu.name))
            throw new WxErrorException(new WxError(1004, "missing name"));
        if (isEmpty(menu.url))
            throw new WxErrorException(new WxError(1004, "missing url"));
    }
}
