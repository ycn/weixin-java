package cc.ycn.common.util;

import cc.ycn.common.bean.WxError;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.exception.WxErrorException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by andy on 12/14/15.
 */
public class RequestTool {

    private final static Logger log = LoggerFactory.getLogger(RequestTool.class);

    private final String tag;
    private final OkHttpClient httpClient;

    public RequestTool(String tag, OkHttpClient httpClient) {
        this.tag = tag;
        this.httpClient = httpClient;
    }

    public <T> T get(String subTag, String url, Class<T> respType) throws WxErrorException {
        return send(subTag, url, respType, ContentType.URL_PARAM, null);
    }

    public <T, D> T post(String subTag, String url, Class<T> respType, ContentType contentType, D reqData) throws WxErrorException {
        return send(subTag, url, respType, contentType, reqData);
    }

    private <T, D> T send(String subTag, String url, Class<T> respType, ContentType contentType, D reqData) throws WxErrorException {

        if (subTag == null || subTag.isEmpty()) {
            log.warn("{} ERR_ARG subTag:null", tag);
            throw new WxErrorException(new WxError(1003, "request failed:" + subTag));
        }

        if (url == null || url.isEmpty()) {
            log.warn("{} ERR_ARG url:null", tag);
            throw new WxErrorException(new WxError(1003, "request failed:" + subTag));
        }

        if (respType == null) {
            log.warn("{} ERR_ARG respType:null", tag);
            throw new WxErrorException(new WxError(1003, "request failed:" + subTag));
        }

        if (contentType == null) {
            log.warn("{} ERR_ARG contentType:null", tag);
            throw new WxErrorException(new WxError(1003, "request failed:" + subTag));
        }

        if (!ContentType.URL_PARAM.equals(contentType)) {
            if (reqData == null) {
                log.warn("{} ERR_ARG reqData:null", tag);
                throw new WxErrorException(new WxError(1003, "request failed:" + subTag));
            }
        }

        // BUILD REQUEST
        Request request;
        RequestBody body;
        String bodyStr;


        switch (contentType) {
            case MEDIA_JSON:
                bodyStr = JsonConverter.pojo2json(reqData);
                body = RequestBody.create(contentType.getMediaType(), bodyStr);
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            case MEDIA_XML:
                bodyStr = XmlConverter.pojo2xml(reqData);
                body = RequestBody.create(contentType.getMediaType(), bodyStr);
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            default:
                request = new Request.Builder()
                        .url(url)
                        .build();
                break;
        }


        String reqSign = getReqSign(request);


        T result = null;

        try {
            // SEND REQUEST
            Response response = httpClient.newCall(request).execute();
            if (response.code() != 200) {
                log.error("{} ERR-{} http_code:{}", tag, reqSign, response.code());
                throw new WxErrorException(new WxError(1002, "request failed:" + subTag));
            }

            // GET RESPONSE
            String respBody = response.body().string();

            if (respBody.contains("errmsg")) {
                log.error("{} ERR-{} http_code:{}, body:{}", tag, reqSign, response.code(), respBody);
                throw new WxErrorException(new WxError(1002, "request failed:" + subTag));
            } else {
                log.info("{} DEBUG-{} body:{}", tag, reqSign, respBody); // XXX
            }

            result = getObject(respBody, contentType, respType, reqSign);

        } catch (IOException e) {
            log.error("{} EX-{} e:{}", tag, reqSign, e.getMessage());
            throw new WxErrorException(new WxError(1002, "request failed:" + subTag));
        }

        return result;
    }


    private String getReqSign(Request request) {
        String sign = StringTool.MD5(System.currentTimeMillis() + request.httpUrl().toString(), 16);
        log.info("{} SEND-{} request:{}", tag, sign, request.httpUrl());
        return sign;
    }

    private <T> T getObject(String body, ContentType contentType, Class<T> type, String sign) throws IOException {
        T t = null;

        switch (contentType) {
            case URL_PARAM:
            case MEDIA_JSON:
                t = JsonConverter.json2pojo(body, type);
                break;
            case MEDIA_XML:
                t = XmlConverter.xml2pojo(body, type);
                break;
        }

        log.info("{} GOT-{} response:{}", tag, sign, JsonConverter.pojo2json(t));

        return t;
    }

}
