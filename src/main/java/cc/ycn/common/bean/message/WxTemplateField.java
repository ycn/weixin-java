package cc.ycn.common.bean.message;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxTemplateField implements Serializable {
    private String value;
    private String color;

    public WxTemplateField(String value) {
        this(value, "#000000");
    }

    public WxTemplateField(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
