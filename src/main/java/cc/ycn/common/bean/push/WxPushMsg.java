package cc.ycn.common.bean.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Created by andy on 12/24/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxPushMsg implements Serializable {
    
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;

    @JacksonXmlProperty(localName = "MsgId")
    private Long msgId;

    @JacksonXmlProperty(localName = "AgentID")
    private Long agentId;

    /**
     * 消息类型以及独有字段
     * text: content
     * image: picUrl, mediaId
     * voice: mediaId, format
     * video: mediaId, thumbMediaId
     * shortvideo: mediaId, thumbMediaId
     * location: locationX, locationY, scale, label
     * link: title, description, url
     */
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    /**
     * 事件类型以及独有字段
     * subscribe: eventKey, ticket
     * unsubscribe:
     * SCAN: eventKey, ticket
     * LOCATION: latitude, longitude, precision
     * CLICK: eventKey
     * VIEW: eventKey
     * scancode_push: eventKey, scanCodeInfo
     * scancode_waitmsg: EventKey, scanCodeInfo
     * enter_agent:
     */
    @JacksonXmlProperty(localName = "Event")
    private String event;


    /* 事件消息字段 */

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "EventKey")
    private String eventKey;

    @JacksonXmlProperty(localName = "Ticket")
    private String ticket;

    @JacksonXmlProperty(localName = "Latitude")
    private Double latitude;

    @JacksonXmlProperty(localName = "Longitude")
    private Double longitude;

    @JacksonXmlProperty(localName = "Precision")
    private Double precision;

    @JacksonXmlProperty(localName = "ScanCodeInfo")
    private WxScanCodeInfo scanCodeInfo;

    /* 普通消息字段 */

    @JacksonXmlProperty(localName = "Content")
    private String content;

    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;

    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;

    @JacksonXmlProperty(localName = "Format")
    private String format;

    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumbMediaId;

    @JacksonXmlProperty(localName = "Location_X")
    private Double locationX;

    @JacksonXmlProperty(localName = "Location_Y")
    private Double locationY;

    @JacksonXmlProperty(localName = "Scale")
    private Integer scale;

    @JacksonXmlProperty(localName = "Label")
    private String label;

    @JacksonXmlProperty(localName = "Title")
    private String title;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "Url")
    private String url;


    public WxPushMsg() {

    }


    public String getUniqId() {
        String prefix = agentId == null ? "" : agentId + ":";
        if (msgId != null) {
            return prefix + msgId + "";
        } else {
            return prefix + fromUserName + ":" + createTime;
        }
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public WxScanCodeInfo getScanCodeInfo() {
        return scanCodeInfo;
    }

    public void setScanCodeInfo(WxScanCodeInfo scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
