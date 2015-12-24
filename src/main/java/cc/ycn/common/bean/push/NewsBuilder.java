package cc.ycn.common.bean.push;

import cc.ycn.common.bean.WxArticle;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.message.WxMsgType;
import cc.ycn.common.exception.WxErrorException;

import java.util.ArrayList;

/**
 * Created by andy on 12/25/15.
 */
public class NewsBuilder extends BaseBuilder<NewsBuilder> {

    public NewsBuilder() {
        message.item = new ArrayList<WxArticle>();
    }

    public NewsBuilder addArticle(WxArticle article) {
        if (article != null)
            message.item.add(article);
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.NEWS;
    }

    @Override
    protected void isValid() {
        if (message.articleCount <= 0 || message.item == null || message.item.isEmpty())
            throw new WxErrorException(new WxError(1004, "missing article"));
    }
}
