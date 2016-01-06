package cc.ycn.wp;

import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.mp.bean.WxOAuthAccessToken;
import cc.ycn.mp.bean.WxUserInfo;

/**
 * 微信网站应用Service
 * 网站应用开发文档: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
 *
 * @author andy
 */
public interface WxWpService {

    /**
     * 获取微信配置
     *
     * @return WxConfig
     */
    WxConfig getConfig();


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
}
