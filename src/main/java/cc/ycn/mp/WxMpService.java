package cc.ycn.mp;

import cc.ycn.common.bean.*;
import cc.ycn.common.bean.WxMediaRef;
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
     * 发送模板消息
     *
     * @param message WxMessage
     * @return WxMsgIdRef
     * @throws WxErrorException
     */
    WxMsgIdRef sendTemplateMessage(WxMessage message) throws WxErrorException;


    /**
     * 设置模板消息所属行业 (每月可修改1次)
     *
     * @param industryId1 String
     * @param industryId2 String
     * @return WxError
     * @throws WxErrorException
     */
    WxError setIndustry(String industryId1, String industryId2) throws WxErrorException;

    /**
     * 添加消息模板 (返回模板ID)
     *
     * @param templateIdShort String
     * @return WxTemplateIdRef
     * @throws WxErrorException
     */
    WxTemplateIdRef addTemplate(String templateIdShort) throws WxErrorException;

    /**
     * 获取现有模板列表
     *
     * @return WxTemplateInfo
     * @throws WxErrorException
     */
    WxTemplateInfo getTemplateInfo() throws WxErrorException;

    /**
     * 删除模版
     *
     * @return WxTemplateErrorMsg
     * @throws WxErrorException
     */
    WxError delTemplate(String templateId) throws WxErrorException;

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
     * @param openId String
     * @return WxUserInfo
     * @throws WxErrorException
     */
    WxUserInfo getUserInfo(String openId) throws WxErrorException;


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
     * 创建个性化菜单
     *
     * @param menu WxMenu
     * @return WxError
     * @throws WxErrorException
     */
    WxError createMatchMenu(WxMenu menu) throws WxErrorException;


    /**
     * 创建二维码ticket
     *
     * @param scanScene WxScanScene
     * @return WxTicket
     * @throws WxErrorException
     */
    WxQRTicket createQRCode(WxScanScene scanScene) throws WxErrorException;


    /**
     * 通过ticket换取二维码
     *
     * @param ticket String
     * @return String
     * @throws WxErrorException
     */
    String showQRCode(String ticket) throws WxErrorException;


    /**
     * 长链接转短链接
     *
     * @param longUrl String
     * @return String
     * @throws WxErrorException
     */
    String toShortUrl(String longUrl) throws WxErrorException;


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


    /**
     * 从缓存里获取WxcardTicket
     *
     * @return String
     */
    String getCardTicket();

    /**
     * 获取WxcardTicket
     *
     * @return WxCardTicket
     * @throws WxErrorException
     */
    WxCardTicket fetchCardTicket() throws WxErrorException;

    /**
     * 获取公众号连网URL
     *
     * @return WxConnectUrl
     * @throws WxErrorException
     */
    WxWifiConnectUrl getConnectUrl() throws WxErrorException;

    /**
     * 上传临时素材
     *
     * @return WxMediaListResp
     * @throws WxErrorException
     */
    WxMediaRef uploadTempMedia(WxTempMediaReq wxTempMediaReq) throws WxErrorException;

    /**
     * 获取素材列表
     * 只支持永久素材
     *
     * @return WxMediaListResp
     * @throws WxErrorException
     */
    WxMediaListResp getPermMediaList(WxMediaListReq wxMediaListReq) throws WxErrorException;
}
