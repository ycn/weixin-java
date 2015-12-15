package cc.ycn.common.bean.message;

/**
 * Created by andy on 12/15/15.
 */
public class FileBuilder extends BaseBuilder<FileBuilder> {

    public FileBuilder() {
        message.file = message.new WxMsgFile();
    }

    public FileBuilder mediaId(String mediaId) {
        if (!isEmpty(mediaId))
            message.file.mediaId = mediaId;
        return this;
    }

    @Override
    protected WxMsgType getType() {
        return WxMsgType.FILE;
    }
}
