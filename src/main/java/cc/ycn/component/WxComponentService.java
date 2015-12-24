package cc.ycn.component;

import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.component.bean.*;

/**
 * 微信开放平台Service
 * 开放平台开发文档: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318587&lang=zh_CN
 *
 * @author andy
 */
public interface WxComponentService {


    /**
     * 获取微信配置
     *
     * @return WxConfig
     */
    WxConfig getConfig();

    /**
     * 从缓存里获取VerifyTicket
     *
     * @return String
     */
    String getVerifyTicket();


    /**
     * 从缓存里获取AccessToken
     *
     * @return String
     */
    String getAccessToken();

    /**
     * 获取AccessToken
     *
     * @return WxComponentAccessToken
     * @throws cc.ycn.common.exception.WxErrorException
     */
    WxComponentAccessToken fetchAccessToken() throws WxErrorException;

    /**
     * 验证AccessToken
     *
     * @param accessToken String
     * @throws WxErrorException
     */
    void verifyAccessToken(String accessToken) throws WxErrorException;


    /**
     * 从缓存获取预授权码
     *
     * @return String
     */
    String getPreAuthCode();

    /**
     * 获取预授权码
     *
     * @return WxPreAuthCode
     * @throws WxErrorException
     */
    WxPreAuthCode createPreAuthCode() throws WxErrorException;


    /**
     * 获取OAuth授权链接
     *
     * @param redirectUrl String
     * @return String
     * @throws WxErrorException
     */
    String getOAuthUrl(String redirectUrl) throws WxErrorException;


    /**
     * 从缓存获取授权方AccessToken
     *
     * @param appId String
     * @return String
     */
    String getAuthorizerAccessToken(String appId);

    /**
     * 从缓存获取授权方RefreshToken
     *
     * @param appId String
     * @return String
     */
    String getAuthorizerRefreshToken(String appId);

    /**
     * 获取授权方AccessToken
     *
     * @param authCode String
     * @return WxAuthorizerAccessInfo
     * @throws WxErrorException
     */
    WxAuthorizerAccessInfo fetchAuthorizerAccessToken(String authCode) throws WxErrorException;


    /**
     * 刷新授权方AccessToken
     *
     * @param appId String
     * @return WxAuthorizerAccessToken
     * @throws WxErrorException
     */
    WxAuthorizerAccessToken refreshAuthorizerAccessToken(String appId) throws WxErrorException;


    /**
     * 获取授权方账户信息
     *
     * @param appId String
     * @return WxAuthorizerInfo
     * @throws WxErrorException
     */
    WxAuthorizerInfoResp getAuthorizerInfo(String appId) throws WxErrorException;


    /**
     * 获取授权方选项信息
     *
     * @param appId      String
     * @param optionName String
     * @return WxOptionValue
     * @throws WxErrorException
     */
    WxOptionValue getAuthorizerOption(String appId, String optionName) throws WxErrorException;


    /**
     * 设置授权方选项信息
     * location_report(地理位置上报选项)       0:无上报, 1:进入会话时上报, 2:每5s上报
     * voice_recognize(语音识别开关选项)       0:关闭语音识别, 1:开启语音识别
     * customer_service(客服开关选项)          0:关闭多客服, 1:开启多客服
     *
     * @param appId       String
     * @param optionName  String
     * @param optionValue String
     * @return WxError
     * @throws WxErrorException
     */
    WxError setAuthorizerOption(String appId, String optionName, String optionValue) throws WxErrorException;
}
