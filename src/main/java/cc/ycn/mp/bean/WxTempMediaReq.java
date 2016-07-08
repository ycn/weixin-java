package cc.ycn.mp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.squareup.okhttp.MediaType;

import java.io.Serializable;

/**
 * Created by ydz on 16/7/1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxTempMediaReq implements Serializable {

    private String type;

    private String filePath;


    public WxTempMediaReq() {
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
