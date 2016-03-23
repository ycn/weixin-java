package cc.ycn.common.bean.push;

import cc.ycn.common.bean.WxArticle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy on 12/24/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "xml")
public class WxAnswerMsg implements Serializable {

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "ToUserName")
    String toUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "FromUserName")
    String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    Long createTime;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "MsgType")
    String msgType;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "Content")
    String content;

    @JacksonXmlProperty(localName = "Image")
    WxMsgImage image;

    @JacksonXmlProperty(localName = "Voice")
    WxMsgVoice voice;

    @JacksonXmlProperty(localName = "Music")
    WxMsgMusic music;

    @JacksonXmlProperty(localName = "Video")
    WxMsgVideo video;

    @JacksonXmlProperty(localName = "ArticleCount")
    Integer articleCount;

    @JacksonXmlElementWrapper(localName = "Articles")
    List<WxArticle> item;

    public String getToUserName() {
        return toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getContent() {
        return content;
    }

    public WxMsgImage getImage() {
        return image;
    }

    public WxMsgVoice getVoice() {
        return voice;
    }

    public WxMsgMusic getMusic() {
        return music;
    }

    public WxMsgVideo getVideo() {
        return video;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public List<WxArticle> getItem() {
        return item;
    }

    // 文本消息
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    // 图片消息
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    @JacksonXmlRootElement(localName = "")
    class WxMsgImage implements Serializable {
        @JacksonXmlCData
        @JacksonXmlProperty(localName = "MediaId")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 语音消息
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    @JacksonXmlRootElement(localName = "")
    class WxMsgVoice implements Serializable {
        @JacksonXmlCData
        @JacksonXmlProperty(localName = "MediaId")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 音乐消息
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    @JacksonXmlRootElement(localName = "")
    class WxMsgMusic implements Serializable {

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "MusicUrl")
        String musicUrl;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "HQMusicUrl")
        String hqMusicUrl;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "ThumbMediaId")
        String thumbMediaId;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "Title")
        String title;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "Description")
        String description;

        public String getMusicUrl() {
            return musicUrl;
        }

        public String getHqMusicUrl() {
            return hqMusicUrl;
        }

        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    // 视频消息
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    @JacksonXmlRootElement(localName = "")
    class WxMsgVideo implements Serializable {
        @JacksonXmlCData
        @JacksonXmlProperty(localName = "MediaId")
        String mediaId;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "Title")
        String title;

        @JacksonXmlCData
        @JacksonXmlProperty(localName = "Description")
        String description;

        public String getMediaId() {
            return mediaId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    // 图文消息
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }
}
