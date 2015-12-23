package cc.ycn.component.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
public class WxOptionValue implements Serializable {

    @JSONField(name = "component_appid")
    private String componentAppId;

    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;

    @JSONField(name = "option_name")
    private String optionName;

    @JSONField(name = "option_value")
    private String optionValue;

    public WxOptionValue() {

    }

    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getAuthorizerAppId() {
        return authorizerAppId;
    }

    public void setAuthorizerAppId(String authorizerAppId) {
        this.authorizerAppId = authorizerAppId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
