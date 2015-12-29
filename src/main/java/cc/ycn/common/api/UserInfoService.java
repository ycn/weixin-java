package cc.ycn.common.api;

import cc.ycn.mp.bean.WxUserInfo;

/**
 * Created by andy on 12/29/15.
 */
public interface UserInfoService {

    String getNickName(String openId);

    void setNickName(String openId, String nickName);

    String getHeadImgUrl(String openId);

    void setHeadImgUrl(String openId, String headImgUrl);

    void setInfo(WxUserInfo info);

    String getField(String openId, String field);

    void setField(String openId, String field, String value);
}
