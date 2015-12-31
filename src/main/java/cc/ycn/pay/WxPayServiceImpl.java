package cc.ycn.pay;

import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.cache.WxConfigCache;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.pay.bean.WxRefundReq;
import cc.ycn.pay.bean.WxRefundResp;
import cc.ycn.pay.bean.WxUnifiedOrderReq;
import cc.ycn.pay.bean.WxUnifiedOrderResp;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * 微信支付Service
 *
 * @author andy
 */
public class WxPayServiceImpl implements WxPayService {

    private final static String LOG_TAG = "[WxPayService]";

    private String appId;
    private WxConfig config;
    private final RequestTool requestTool;

    public WxPayServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxConfigCache wxConfigCache = WxConfigCache.getInstance();
        this.config = wxConfigCache == null ? null : wxConfigCache.get(appId);
        if (this.config == null) {
            throw new WxErrorException(new WxError(1001, "missing config:" + appId));
        }

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(WxConstant.WX_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(WxConstant.WX_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(WxConstant.WX_WRITE_TIMEOUT, TimeUnit.SECONDS);

        requestTool = new RequestTool(LOG_TAG, httpClient);
    }

    @Override
    public WxConfig getConfig() {
        return config;
    }

    @Override
    public WxUnifiedOrderResp prepay(WxUnifiedOrderReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        req.setAppId(appId);
        req.setMchId(config.getMchId());

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        return requestTool.post(
                "prepay",
                url,
                WxUnifiedOrderResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    @Override
    public WxRefundResp refund(WxRefundReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

//        req.setAppId(appId);
//        req.setMchId(config.getMchId());

        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

        return requestTool.post(
                "refund",
                url,
                WxRefundResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }
}
