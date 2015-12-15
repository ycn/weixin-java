package cc.ycn.mp;

import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.mp.bean.WxIpList;
import cc.ycn.mp.bean.WxKfAccount;
import cc.ycn.mp.bean.WxOAuthAccessToken;

/**
 * 微信公众号Service
 * 公众号开发文档: http://mp.weixin.qq.com/wiki/home/
 *
 * @author andy
 */
public interface WxMpService {

    /**
     * 获取AccessToken
     *
     * @return String
     */
    String getAccessToken();

    WxAccessToken fetchAccessToken() throws WxErrorException;

    void verifyAccessToken(String accessToken) throws WxErrorException;

    /**
     * 获取微信IP列表
     *
     * @param accessToken String
     * @return WxIpList
     * @throws WxErrorException
     */
    WxIpList getIPList(String accessToken) throws WxErrorException;


    /**
     * 主动发消息
     *
     * @param message WxMessage
     * @return WxError
     * @throws WxErrorException
     */
    WxError sendMessage(WxMessage message) throws WxErrorException;

    /**
     * 添加客服帐号
     *
     * @param account WxKfAccount
     * @return WxError
     * @throws WxErrorException
     */
    WxError createKfAccount(WxKfAccount account) throws WxErrorException;

    /**
     * 更新客服帐号
     *
     * @param account WxKfAccount
     * @return WxError
     * @throws WxErrorException
     */
    WxError updateKfAccount(WxKfAccount account) throws WxErrorException;

    /**
     * 删除客服帐号
     *
     * @param account WxKfAccount
     * @return WxError
     * @throws WxErrorException
     */
    WxError deleteKfAccount(WxKfAccount account) throws WxErrorException;


    /**
     * 获取OAuth授权链接
     *
     * @param state       String
     * @param scope       WxOAuthScope
     * @param redirectUrl String
     * @return String
     */
    String getOAuthUrl(String state, WxOAuthScope scope, String redirectUrl) throws WxErrorException;


    /**
     * 获取OAuth授权AccessToken 和 用户唯一标识
     *
     * @param code String
     * @return WxOAuthAccessToken
     * @throws WxErrorException
     */
    WxOAuthAccessToken getOAuthAccessToken(String code) throws WxErrorException;
}
