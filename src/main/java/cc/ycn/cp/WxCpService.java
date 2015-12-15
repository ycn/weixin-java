package cc.ycn.cp;

import cc.ycn.common.bean.WxAccessToken;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxOAuthScope;
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
     * 获取AccessToken
     *
     * @return String
     */
    String getAccessToken();

    WxAccessToken fetchAccessToken() throws WxErrorException;

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

}
