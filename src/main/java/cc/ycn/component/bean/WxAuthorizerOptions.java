package cc.ycn.component.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by andy on 12/28/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAuthorizerOptions implements Serializable {

    private String locationReport;

    private String voiceRecognize;

    private String customerService;

    public WxAuthorizerOptions() {

    }


    public String getLocationReport() {
        return locationReport;
    }

    public void setLocationReport(String locationReport) {
        this.locationReport = locationReport;
    }

    public String getVoiceRecognize() {
        return voiceRecognize;
    }

    public void setVoiceRecognize(String voiceRecognize) {
        this.voiceRecognize = voiceRecognize;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }
}
