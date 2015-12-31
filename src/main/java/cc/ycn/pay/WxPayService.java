package cc.ycn.pay;

import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.pay.bean.WxRefundReq;
import cc.ycn.pay.bean.WxRefundResp;
import cc.ycn.pay.bean.WxUnifiedOrderReq;
import cc.ycn.pay.bean.WxUnifiedOrderResp;

/**
 * 微信支付Service
 * 普通商户: https://pay.weixin.qq.com/wiki/doc/api/index.html
 * 服务商: https://pay.weixin.qq.com/wiki/doc/api/sl.html
 *
 * @author andy
 */
public interface WxPayService {

    /**
     * 获取微信配置
     *
     * @return WxConfig
     */
    WxConfig getConfig();


    /**
     * 统一下单
     *
     * @param req WxUnifiedOrderReq
     * @return WxUnifiedOrderResp
     * @throws WxErrorException
     */
    WxUnifiedOrderResp prepay(WxUnifiedOrderReq req) throws WxErrorException;


    /**
     * 申请退款
     *
     * @param req WxRefundReq
     * @return WxRefundResp
     * @throws WxErrorException
     */
    WxRefundResp refund(WxRefundReq req) throws WxErrorException;
}
