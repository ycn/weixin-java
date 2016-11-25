package cc.ycn.common.constant;

import com.squareup.okhttp.MediaType;

import java.util.regex.Pattern;

/**
 * Created by andy on 12/14/15.
 */
public class WxConstant {

    public final static int WX_CONNECT_TIMEOUT = 30;
    public final static int WX_READ_TIMEOUT = 30;
    public final static int WX_WRITE_TIMEOUT = 30;

    public final static Pattern ACCESS_TOKEN_PARAM_PATTERN = Pattern.compile("access_token=([a-zA-Z0-9-_]+).*$");

    public static final MediaType MEDIA_TYPE_FORM = MediaType.parse("multipart/form-data");
}
