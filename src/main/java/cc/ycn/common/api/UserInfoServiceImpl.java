package cc.ycn.common.api;

import cc.ycn.common.util.RedisDb;
import cc.ycn.common.util.StringTool;
import cc.ycn.mp.bean.WxUserInfo;

/**
 * Created by andy on 12/29/15.
 */
public class UserInfoServiceImpl implements UserInfoService {

    private final RedisDb db;

    public UserInfoServiceImpl(String host, int port) {
        this.db = new RedisDb(host, port, 12);
    }


    @Override
    public String getNickName(String openId) {
        if (StringTool.isEmpty(openId))
            return "";
        String nickName = db.hget(openId, "nickname");
        return StringTool.getString(nickName);
    }

    @Override
    public void setNickName(String openId, String nickName) {
        if (StringTool.isEmpty(openId) || StringTool.isEmpty(nickName))
            return;
        db.hset(openId, "nickname", nickName);
    }

    @Override
    public String getHeadImgUrl(String openId) {
        if (StringTool.isEmpty(openId))
            return "";
        String headImgUrl = db.hget(openId, "headimgurl");
        return StringTool.getString(headImgUrl);
    }

    @Override
    public void setHeadImgUrl(String openId, String headImgUrl) {
        if (StringTool.isEmpty(openId) || StringTool.isEmpty(headImgUrl))
            return;
        db.hset(openId, "headimgurl", headImgUrl);
    }

    @Override
    public void setInfo(WxUserInfo info) {
        if (info == null)
            return;
        db.hset(info.getOpenId(), "nickname", info.getNickName());
        db.hset(info.getOpenId(), "headimgurl", info.getHeadImgUrl());
    }

    @Override
    public String getField(String openId, String field) {
        if (StringTool.isEmpty(openId) || StringTool.isEmpty(field))
            return "";
        return db.hget(openId, field);
    }

    @Override
    public void setField(String openId, String field, String value) {
        if (StringTool.isEmpty(openId) || StringTool.isEmpty(field) || StringTool.isEmpty(value))
            return;
        db.hset(openId, field, value);
    }
}
