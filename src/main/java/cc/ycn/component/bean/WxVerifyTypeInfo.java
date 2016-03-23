package cc.ycn.component.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/23/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxVerifyTypeInfo implements Serializable {

    private String id;

    public WxVerifyTypeInfo() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
