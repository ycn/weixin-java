package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;

import java.util.HashMap;

/**
 * Created by andy on 12/24/15.
 */
public class TemplateBuilder extends BaseBuilder<TemplateBuilder> {

    public TemplateBuilder() {
        message.data = new HashMap<String, WxTemplateField>();
    }

    public TemplateBuilder addField(String key, String value) {
        return addField(key, value, "#000000");
    }

    public TemplateBuilder addField(String key, String value, String color) {
        if (isEmpty(key) || isEmpty(value) || isEmpty(color))
            return this;

        message.data.put(key, new WxTemplateField(value, color));
        return this;
    }

    public TemplateBuilder url(String url) {
        if (isEmpty(url))
            return this;

        message.url = url;
        return this;
    }

    public TemplateBuilder templateId(String templateId) {
        if (isEmpty(templateId))
            return this;

        message.templateId = templateId;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.TEMPLATE;
    }

    @Override
    protected void isValid() {
        if (isEmpty(message.templateId))
            throw new WxErrorException(new WxError(1004, "missing templateId"));
        if (isEmpty(message.url))
            throw new WxErrorException(new WxError(1004, "missing url"));
        if (message.data == null || message.data.isEmpty())
            throw new WxErrorException(new WxError(1004, "missing data"));
    }
}
