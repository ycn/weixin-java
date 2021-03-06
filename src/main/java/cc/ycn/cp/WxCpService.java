package cc.ycn.cp;

import cc.ycn.common.bean.*;
import cc.ycn.common.bean.menu.WxMenu;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.cp.bean.WxDepartmentList;
import cc.ycn.cp.bean.WxQyUser;

/**
 * 微信企业号Service
 * 企业号开发文档: http://qydev.weixin.qq.com/wiki/
 *
 * @author andy
 */
public interface WxCpService {

    /**
     * 获取微信配置
     *
     * @return WxConfig
     */
    WxMsgConfig getConfig();

    /**
     * 从缓存里获取AccessToken
     *
     * @return String
     */
    String getAccessToken();

    /**
     * 获取AccessToken
     *
     * @return WxAccessToken
     * @throws WxErrorException
     */
    WxAccessToken fetchAccessToken() throws WxErrorException;

    /**
     * 验证AccessToken
     *
     * @param accessToken String
     * @throws WxErrorException
     */
    void verifyAccessToken(String accessToken) throws WxErrorException;

    /**
     * 获取所有部门信息
     *
     * @param accessToken String
     * @return WxDepartmentList
     * @throws WxErrorException
     */
    WxDepartmentList getDepartments(String accessToken) throws WxErrorException;

    /**
     * 主动发消息
     *
     * @param message WxMessage
     * @return WxError
     * @throws WxErrorException
     */
    WxError sendMessage(WxMessage message) throws WxErrorException;

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
     * 获取用户唯一标识
     *
     * @param code String
     * @return WxQyUser
     * @throws WxErrorException
     */
    WxQyUser getUserId(String code) throws WxErrorException;


    /**
     * 换取用户OpenId
     *
     * @param openIdRef WxOpenIdRef
     * @return WxOpenIdRef
     * @throws WxErrorException
     */
    WxOpenIdRef toOpenId(WxOpenIdRef openIdRef) throws WxErrorException;


    /**
     * 换取用户UserId
     *
     * @param userIdRef WxOpenIdRef
     * @return WxOpenIdRef
     * @throws WxErrorException
     */
    WxOpenIdRef toUserId(WxOpenIdRef userIdRef) throws WxErrorException;


    /**
     * 创建自定义菜单
     *
     * @param menu WxMenu
     * @return WxError
     * @throws WxErrorException
     */
    WxError createMenu(String agentId, WxMenu menu) throws WxErrorException;


    /**
     * 删除自定义菜单
     *
     * @param agentId String
     * @return WxError
     * @throws WxErrorException
     */
    WxError deleteMenu(String agentId) throws WxErrorException;

    /**
     * 从缓存里获取JSTicket
     *
     * @return String
     */
    String getJSTicket();

    /**
     * 获取JSTicket
     *
     * @return WxJSTicket
     * @throws WxErrorException
     */
    WxJSTicket fetchJSTicket() throws WxErrorException;

    /**
     * 生成微信JS Signature
     *
     * @param url String
     * @return WxJSSign
     */
    WxJSSignature createJSSignature(String url);

}
