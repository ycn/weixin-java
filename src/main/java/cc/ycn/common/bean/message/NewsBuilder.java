package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

import java.util.ArrayList;

/**
 * Created by andy on 12/15/15.
 */
public class NewsBuilder extends BaseBuilder<NewsBuilder> {

    public NewsBuilder() {
        message.news = message.new WxMsgNews();
        message.news.articles = new ArrayList<WxArticle>();
    }

    public NewsBuilder addArticle(WxArticle article) {
        if (article != null)
            message.news.articles.add(article);
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.NEWS;
    }

    @Override
    protected void isValid() {
        if (message.news.articles.isEmpty())
            throw new WxErrorException(new WxError(1004, "missing articles"));
    }
}
