package cc.ycn.common.bean.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Created by andy on 12/22/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxEncryptResp implements Serializable {

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "MsgSignature")
    private String msgSignature;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "TimeStamp")
    private String timeStamp;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "Nonce")
    private String nonce;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "Encrypt")
    private String encrypt;

    public WxEncryptResp() {

    }


    public String getMsgSignature() {
        return msgSignature;
    }

    public void setMsgSignature(String msgSignature) {
        this.msgSignature = msgSignature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
