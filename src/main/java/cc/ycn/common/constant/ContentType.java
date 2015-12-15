package cc.ycn.common.constant;

import com.squareup.okhttp.MediaType;

/**
 * Created by andy on 12/14/15.
 */
public enum ContentType {

    URL_PARAM(MediaType.parse("text/plain; charset=utf-8")),
    MEDIA_JSON(MediaType.parse("application/json; charset=utf-8")),
    MEDIA_XML(MediaType.parse("application/xml; charset=utf-8"));

    private MediaType mediaType;

    private ContentType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
