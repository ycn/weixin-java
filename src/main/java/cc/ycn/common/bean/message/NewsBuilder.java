package cc.ycn.common.bean.message;

import java.util.HashSet;

/**
 * Created by andy on 12/15/15.
 */
public class NewsBuilder extends BaseBuilder<NewsBuilder> {

    public NewsBuilder() {
        message.news = message.new WxMsgNews();
        message.news.articles = new HashSet<WxArticle>();
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
}
