package cc.ycn.common.api;

import cc.ycn.common.bean.WxError;

/**
 * Created by andy on 1/5/16.
 */
public interface WxErrorHandler {

    boolean shouldRetryOnWxError(WxError error);

    String getRetryUrl(String url, WxError error);
}
