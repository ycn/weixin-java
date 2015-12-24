package cc.ycn.common.bean.message;

import cc.ycn.common.bean.WxArticle;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 发送微信消息的实体
 *
 * @author andy
 */
public class WxMessage implements Serializable {

    @JSONField(name = "touser")
    String toUser;

    @JSONField(name = "toparty")
    String toParty;

    @JSONField(name = "totag")
    String toTag;

    @JSONField(name = "msgtype")
    String msgType;

    @JSONField(name = "agentid")
    String agentId;

    Integer safe;

    WxMsgText text;

    WxMsgImage image;

    WxMsgVoice voice;

    WxMsgVideo video;

    WxMsgFile file;

    WxMsgMusic music;

    WxMsgNews news;

    @JSONField(name = "mpnews")
    WxMsgMpnews mpNews;

    @JSONField(name = "wxcard")
    WxMsgWxcard wxCard;

    @JSONField(name = "customservice")
    WxMsgKf customService;

    @JSONField(name = "template_id")
    String templateId;

    String url;

    Map<String, WxTemplateField> data;

    WxMessage() {

    }

    public String getToUser() {
        return toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public String getToTag() {
        return toTag;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getAgentId() {
        return agentId;
    }

    public Integer getSafe() {
        return safe;
    }

    public WxMsgText getText() {
        return text;
    }

    public WxMsgImage getImage() {
        return image;
    }

    public WxMsgVoice getVoice() {
        return voice;
    }

    public WxMsgVideo getVideo() {
        return video;
    }

    public WxMsgFile getFile() {
        return file;
    }

    public WxMsgMusic getMusic() {
        return music;
    }

    public WxMsgNews getNews() {
        return news;
    }

    public WxMsgWxcard getWxCard() {
        return wxCard;
    }

    public WxMsgKf getCustomService() {
        return customService;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, WxTemplateField> getData() {
        return data;
    }

    // 文本消息
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    class WxMsgText implements Serializable {
        String content;

        public String getContent() {
            return content;
        }
    }

    // 图片消息
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    class WxMsgImage implements Serializable {
        @JSONField(name = "media_id")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 语音消息
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    class WxMsgVoice implements Serializable {
        @JSONField(name = "media_id")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 视频消息
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    class WxMsgVideo implements Serializable {
        @JSONField(name = "media_id")
        String mediaId;

        @JSONField(name = "thumb_media_id")
        String thumbMediaId;

        String title;

        String description;

        public String getMediaId() {
            return mediaId;
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

    // 文件消息
    public static FileBuilder FILE() {
        return new FileBuilder();
    }

    class WxMsgFile implements Serializable {
        @JSONField(name = "media_id")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 音乐消息
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    class WxMsgMusic implements Serializable {
        @JSONField(name = "musicurl")
        String musicUrl;

        @JSONField(name = "hqmusicurl")
        String hqMusicUrl;

        @JSONField(name = "thumb_media_id")
        String thumbMediaId;

        String title;

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

    // 图文消息
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    class WxMsgNews implements Serializable {
        List<WxArticle> articles;

        public List<WxArticle> getArticles() {
            return articles;
        }
    }

    // 永久图文消息
    public static MpnewsBuilder MPNEWS() {
        return new MpnewsBuilder();
    }

    class WxMsgMpnews implements Serializable {
        @JSONField(name = "media_id")
        String mediaId;

        public String getMediaId() {
            return mediaId;
        }
    }

    // 微信卡券
    public static WxcardBuilder WXCARD() {
        return new WxcardBuilder();
    }

    class WxMsgWxcard implements Serializable {
        @JSONField(name = "card_id")
        String cardId;

        @JSONField(name = "card_ext")
        String cardExt;

        public String getCardId() {
            return cardId;
        }

        public String getCardExt() {
            return cardExt;
        }
    }

    // 客服帐号
    class WxMsgKf implements Serializable {
        @JSONField(name = "kf_account")
        String kfAccount;

        public WxMsgKf(String kfAccount) {
            this.kfAccount = kfAccount;
        }

        public String getKfAccount() {
            return kfAccount;
        }
    }

    // 模板消息
    public static TemplateBuilder TEMPLATE() {
        return new TemplateBuilder();
    }
}
