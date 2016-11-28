package cc.ycn.pay;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.bean.WxPayConfig;
import cc.ycn.common.cache.WxMsgConfigCache;
import cc.ycn.common.cache.WxPayConfigCache;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import cc.ycn.common.util.RequestTool;
import cc.ycn.common.util.StringTool;
import cc.ycn.pay.bean.*;
import com.squareup.okhttp.OkHttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

/**
 * 微信支付Service
 *
 * @author andy
 */
public class WxPayServiceImpl implements WxPayService {

    private final static Logger log = LoggerFactory.getLogger(WxPayServiceImpl.class);
    private final static String LOG_TAG = "[WxPayService]";

    private String appId;
    private WxMsgConfig config;
    private WxPayConfig payConfig;
    private final RequestTool requestTool;

    public WxPayServiceImpl(String appId) throws WxErrorException {
        this.appId = appId;

        WxMsgConfigCache wxMsgConfigCache = WxMsgConfigCache.getInstance();
        this.config = wxMsgConfigCache == null ? null : wxMsgConfigCache.get(appId);
        if (this.config == null) {
            throw new WxErrorException(new WxError(1001, "missing config:" + appId));
        }

        WxPayConfigCache wxPayConfigCache = WxPayConfigCache.getInstance();
        this.payConfig = wxPayConfigCache == null ? null : wxPayConfigCache.get(appId);
        if (payConfig == null) {
            throw new WxErrorException(new WxError(1001, "missing pay config:" + appId));
        }

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(WxConstant.WX_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(WxConstant.WX_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(WxConstant.WX_WRITE_TIMEOUT, TimeUnit.SECONDS);

        String mchId = payConfig.getMchid();

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream stream = ClassLoader.getSystemResourceAsStream(mchId + ".p12");
            try {
                keyStore.load(stream, mchId.toCharArray());
            } finally {
                if (stream != null)
                    stream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mchId.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            httpClient.setSslSocketFactory(sslcontext.getSocketFactory());

        } catch (Exception e) {
            throw new WxErrorException(new WxError(1001, "ssl config failed:" + appId + ", check your " + mchId + ".p12"));
        }

        requestTool = new RequestTool(LOG_TAG, httpClient);
    }

    @Override
    public WxMsgConfig getConfig() {
        return config;
    }

    @Override
    public WxUnifiedOrderResp prepay(WxUnifiedOrderReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        updateSign(req);

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

        updateSign(req);

        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

        return requestTool.post(
                "refund",
                url,
                WxRefundResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    @Override
    public WxCloseResp close(WxCloseReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        updateSign(req);

        String url = "https://api.mch.weixin.qq.com/pay/closeorder";

        return requestTool.post(
                "close",
                url,
                WxCloseResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    @Override
    public WxShortUrlResp shortUrl(WxShortUrlReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        updateSign(req);

        String url = "https://api.mch.weixin.qq.com/tools/shorturl";

        return requestTool.post(
                "shortUrl",
                url,
                WxShortUrlResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    @Override
    public WxOrderQueryResp orderQuery(WxOrderQueryReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        updateSign(req);

        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        return requestTool.post(
                "orderQuery",
                url,
                WxOrderQueryResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    @Override
    public WxRefundQueryResp refundQuery(WxRefundQueryReq req) throws WxErrorException {
        if (req == null)
            throw new WxErrorException(new WxError(1004, "null req"));

        updateSign(req);

        String url = "https://api.mch.weixin.qq.com/pay/refundquery";

        return requestTool.post(
                "refundQuery",
                url,
                WxRefundQueryResp.class,
                ContentType.MEDIA_XML,
                req
        );
    }

    private void updateSign(WxPayBaseReq req) {

        if (payConfig.isAuthorizer()) {
            req.setAppid(payConfig.getComAppid());
            req.setMch_id(payConfig.getMchid());
            req.setSub_appid(appId);
        } else {
            req.setAppid(appId);
            if (!StringTool.isEmpty(payConfig.getMchid()))
                req.setMch_id(payConfig.getMchid());
        }

        req.setSign(config, payConfig);
    }
}
