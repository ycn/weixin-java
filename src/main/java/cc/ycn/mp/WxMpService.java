package cc.ycn.mp;

import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
import cc.ycn.common.bean.WxTicket;
import cc.ycn.common.bean.menu.WxMenu;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.mp.bean.*;

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


    /**
     * 获取用户资料
     *
     * @param oauthAccessToken String
     * @param openId           String
     * @return WxUserInfo
     * @throws WxErrorException
     */
    WxUserInfo getUserInfo(String oauthAccessToken, String openId) throws WxErrorException;


    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param oauthAccessToken String
     * @return WxError
     * @throws WxErrorException
     */
    WxError checkOAuthAccessToken(String oauthAccessToken, String openId) throws WxErrorException;


    /**
     * 创建自定义菜单
     *
     * @param menu WxMenu
     * @return WxError
     * @throws WxErrorException
     */
    WxError createMenu(WxMenu menu) throws WxErrorException;

    /**
     * 删除自定义菜单
     *
     * @return WxError
     * @throws WxErrorException
     */
    WxError deleteMenu() throws WxErrorException;


    /**
     * 创建二维码ticket
     *
     * @param scanScene WxScanScene
     * @return WxTicket
     * @throws WxErrorException
     */
    WxTicket createQRCode(WxScanScene scanScene) throws WxErrorException;


    /**
     * 通过ticket换取二维码
     *
     * @param ticket String
     * @return String
     * @throws WxErrorException
     */
    String showQRCode(String ticket) throws WxErrorException;
}
