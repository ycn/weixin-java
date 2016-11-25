package cc.ycn.common.util;

import cc.ycn.common.api.WxErrorHandler;
import cc.ycn.common.bean.WxError;
import cc.ycn.common.constant.ContentType;
import cc.ycn.common.constant.WxConstant;
import cc.ycn.common.exception.WxErrorException;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by andy on 12/14/15.
 */
public class RequestTool {

    private final static Logger log = LoggerFactory.getLogger(RequestTool.class);
    private final static int MAX_RETRY = 3;


    private final String tag;
    private final OkHttpClient httpClient;
    private WxErrorHandler errorHandler;

    public RequestTool(String tag, OkHttpClient httpClient) {
        this.tag = tag;
        this.httpClient = httpClient;
    }

    public RequestTool(String tag, OkHttpClient httpClient, WxErrorHandler handler) {
        this.tag = tag;
        this.httpClient = httpClient;
        this.errorHandler = handler;
    }

    public <T> T get(String subTag, String url, Class<T> respType) throws WxErrorException {
        return send(subTag, url, respType, ContentType.URL_PARAM, null, 1, false);
    }

    public <T, D> T post(String subTag, String url, Class<T> respType, ContentType contentType, D reqData) throws WxErrorException {
        return send(subTag, url, respType, contentType, reqData, 1, false);
    }

    public <T> T upload(String subTag, String url, Class<T> respType, ContentType contentType, String filePath) throws WxErrorException {
        return send(subTag, url, respType, contentType, filePath, 1, true);
    }


    private <T, D> T send(String subTag, String url, Class<T> respType, ContentType contentType, D reqData, int retry, boolean isUpload) throws WxErrorException {

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

        if (retry < 1) retry = 1;

        // BUILD REQUEST
        Request request = null;
        RequestBody body = null;
        String bodyStr = null;

        if (!isUpload) {
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
        } else {

            String filePath = (String) reqData;
            String[] parts = filePath.split("/");
            if (parts.length == 0) {
                log.error("{} WXERR-400 filePath error. filePath:{}", tag, filePath);
                throw new WxErrorException(new WxError(1002, "get errmsg:" + subTag));
            }
            String fileName = parts[parts.length - 1];

            File file = new File(filePath);
            if (!file.exists() || !file.canRead()) {
                log.error("{} WXERR-400 file cannot read. filePath:{}", tag, filePath);
                throw new WxErrorException(new WxError(1002, "get errmsg:" + subTag));
            }

            body = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"image\"; filename=\"" + fileName + "\""),
                            RequestBody.create(WxConstant.MEDIA_TYPE_FORM, file))
                    .build();

            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
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

            // pre catch err
            if (respBody.contains("errmsg")) {
                switch (contentType) {
                    case URL_PARAM:
                    case MEDIA_JSON:
                        WxError wxError = JsonConverter.json2pojo(respBody, WxError.class);
                        if (wxError != null && wxError.getErrcode() > 0) {

                            if (retry <= MAX_RETRY && errorHandler != null && errorHandler.shouldRetryOnWxError(wxError)) {
                                log.info("{} WXRETRY-{} http_code:{}, req:{} body:{} retry:{}",
                                        tag, reqSign, response.code(), bodyStr, respBody, retry);

                                // RETRY
                                String retryUrl = errorHandler.getRetryUrl(url, wxError);
                                return send(subTag, retryUrl, respType, contentType, reqData, ++retry, isUpload);

                            } else {
                                log.error("{} WXERR-{} http_code:{}, req:{} body:{}",
                                        tag, reqSign, response.code(), bodyStr, respBody);

                                throw new WxErrorException(wxError);
//                                throw new WxErrorException(new WxError(1002, "get errmsg:" + subTag));
                            }
                        }
                        break;
                    case MEDIA_XML:
                        break;
                }
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
