package cc.ycn.common.api;

import cc.ycn.common.bean.WxToken;

/**
 * @author andy <andy@ycn.cc>
 */
public interface WxTokenHandler {

    WxToken get(String key);

    Boolean set(String key, WxToken token);
}
