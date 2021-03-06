package cc.ycn.common;

import cc.ycn.common.bean.WxMsgConfig;
import cc.ycn.common.bean.WxPayConfig;
import cc.ycn.common.util.StringTool;
import cc.ycn.weixin.aes.AesException;
import cc.ycn.weixin.aes.WXBizMsgCrypt;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 微信签名工具
 *
 * @author andy
 */
public class WeixinSignTool {

    private final static Logger log = LoggerFactory.getLogger(WeixinSignTool.class);
    private final static String LOG_TAG = "[WeixinSignTool]";

    /**
     * 验证推送消息的正确性
     *
     * @return boolean
     */
    public static boolean checkSignature(WxMsgConfig config, String signature, String timeStamp, String nonce) {
        if (config == null)
            return false;

        if (signature == null || signature.isEmpty())
            return false;

        if (timeStamp == null || timeStamp.isEmpty())
            return false;

        if (nonce == null || nonce.isEmpty())
            return false;

        String sign = WeixinSignTool.createSignature(config.getToken(), timeStamp, nonce);

        return signature.equals(sign);
    }

    /**
     * 验证推送的加密消息的正确性
     *
     * @return boolean
     */
    public static boolean checkMsgSignature(WxMsgConfig config, String msgSignature, String timeStamp, String nonce, String msgEncrypt) {
        if (config == null)
            return false;

        if (msgSignature == null || msgSignature.isEmpty())
            return false;

        if (timeStamp == null || timeStamp.isEmpty())
            return false;

        if (nonce == null || nonce.isEmpty())
            return false;

        if (msgEncrypt == null)
            msgEncrypt = "";

        String sign = WeixinSignTool.createSignature(config.getToken(), timeStamp, nonce, msgEncrypt);

        return msgSignature.equals(sign);
    }


    /**
     * 校验Url
     *
     * @param msgSignature   String
     * @param timeStamp      String
     * @param nonce          String
     * @param echoStrEncrypt String 密文echoStr
     * @return String 明文echoStr
     * @throws cc.ycn.common.exception.WxErrorException
     */
    public static String verifyUrl(WxMsgConfig config, String msgSignature, String timeStamp, String nonce, String echoStrEncrypt) {
        if (config == null)
            return null;

        if (msgSignature == null || msgSignature.isEmpty())
            return null;

        if (timeStamp == null || timeStamp.isEmpty())
            return null;

        if (nonce == null || nonce.isEmpty())
            return null;

        if (echoStrEncrypt == null || echoStrEncrypt.isEmpty())
            return null;

        String echoStr = null;
        try {
            WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppid(), config.getComAppid());
            echoStr = msgCrypt.VerifyURL(msgSignature, timeStamp, nonce, echoStrEncrypt);
        } catch (Exception ignore) {
        }

        return echoStr;
    }

    /**
     * 加密微信消息
     *
     * @param config WxConfig
     * @param text   String
     * @return String
     * @throws AesException
     */
    public static String encrypt(WxMsgConfig config, String text) throws AesException {
        if (config == null || text == null || text.isEmpty())
            return "";
        WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppid(), config.getComAppid());
        return msgCrypt.encrypt(text);
    }

    /**
     * 解密微信消息
     *
     * @param config WxConfig
     * @param text   String
     * @return String
     * @throws AesException
     */
    public static String decrypt(WxMsgConfig config, String text) throws AesException {
        if (config == null || text == null || text.isEmpty())
            return "";
        WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppid(), config.getComAppid());
        return msgCrypt.decrypt(text);
    }

    /**
     * 加密微信回复消息
     *
     * @param config   WxConfig
     * @param replyMsg xml-formatted-string
     * @return xml-formatted-string
     * @throws AesException
     */
    public static String encryptMsg(WxMsgConfig config, String replyMsg) throws AesException {
        if (config == null || replyMsg == null || replyMsg.isEmpty())
            return "";
        WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppid(), config.getComAppid());
        String randomStr = StringTool.getRandomStr(8);
        return msgCrypt.EncryptMsg(replyMsg, "", randomStr);
    }


    /**
     * 计算微信参数SHA1签名
     *
     * @param params String[]
     * @return String
     */
    public static String createSignature(String... params) {
        String packValue = packValue(Arrays.asList(params), "");
        return StringTool.SHA1(packValue);
    }

    /**
     * 计算微信JS参数SHA1签名
     *
     * @param packValue String
     * @return String
     */
    public static String createJSSignature(String packValue) {
        return StringTool.SHA1(packValue);
    }

    /**
     * 计算微信卡券参数SHA1签名
     *
     * @param packValue String
     * @return String
     */
    public static String createCardSignature(String packValue) {
        return StringTool.SHA1(packValue);
    }


    /**
     * 计算微信支付参数MD5签名
     *
     * @param config        WxConfig
     * @param obj           Object
     * @param excludeFields String[]
     * @return String
     */
    public static String createPaySignature(WxMsgConfig config, WxPayConfig payConfig, Object obj, String[] excludeFields) {
        if (config == null || obj == null)
            return "";
        String packVal = packValue(obj, "&", excludeFields) + "&key=" + payConfig.getSecret();
        return StringTool.MD5(packVal).toUpperCase();
    }

    /**
     * 计算微信支付参数MD5签名
     *
     * @param config        WxConfig
     * @param map           Map
     * @param excludeFields String[]
     * @return String
     */
    public static String createPaySignature(WxMsgConfig config, WxPayConfig payConfig, Map<String, String> map, String[] excludeFields) {
        if (config == null || map == null || map.isEmpty())
            return "";
        String packVal = packValue(map, "&", excludeFields) + "&key=" + payConfig.getSecret();
        return StringTool.MD5(packVal).toUpperCase();
    }

    /**
     * 计算微信支付参数MD5签名
     *
     * @param config        WxConfig
     * @param arr           List
     * @param excludeFields String[]
     * @return String
     */
    public static String createPaySignature(WxMsgConfig config, WxPayConfig payConfig, List<String> arr, String[] excludeFields) {
        if (config == null || arr == null || arr.isEmpty())
            return "";
        String packVal = packValue(arr, "&", excludeFields) + "&key=" + payConfig.getSecret();
        return StringTool.MD5(packVal).toUpperCase();
    }

    /**
     * 微信参数打包
     *
     * @param obj Object
     * @param sep String
     * @return String
     */
    public static String packValue(Object obj, String sep) {
        return packValue(obj, sep, null);
    }


    /**
     * 微信参数打包
     *
     * @param obj           Object
     * @param sep           String
     * @param excludeFields String[]
     * @return String
     */
    public static String packValue(Object obj, String sep, String[] excludeFields) {

        if (obj == null)
            return "";

        StandardToStringStyle style = new StandardToStringStyle();
        style.setUseIdentityHashCode(false);
        style.setUseClassName(false);
        style.setUseShortClassName(false);
        style.setUseFieldNames(true);
        style.setContentStart("");
        style.setContentEnd("");
        style.setFieldNameValueSeparator("=");
        style.setFieldSeparator(sep);
        style.setArrayStart("[");
        style.setArrayEnd("]");
        style.setArraySeparator(",");
        style.setNullText("<null>");

        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj, style);

        if (excludeFields != null && excludeFields.length > 0)
            builder = builder.setExcludeFieldNames(excludeFields);

        String value = builder.toString();
        String[] parts = value.split(sep);

        List<String> list = new ArrayList<String>();
        for (String part : parts) {
            System.out.println(part);
            if (part == null
                    || part.isEmpty()
                    || part.contains("<null>")
                    || (part.indexOf("=") == (part.length() - 1))) continue;
            list.add(part);
        }

        Collections.sort(list);

        String packVal = Joiner.on(sep).join(list);
        log.info("{} packVal={}", LOG_TAG, packVal);
        return packVal;
    }

    /**
     * 微信参数打包
     *
     * @param map Map
     * @param sep String
     * @return String
     */
    public static String packValue(Map<String, String> map, String sep) {
        return packValue(map, sep, null);
    }

    /**
     * 微信参数打包
     *
     * @param map         Map
     * @param sep         String
     * @param excludeKeys String[]
     * @return String
     */
    public static String packValue(Map<String, String> map, String sep, String[] excludeKeys) {
        if (map == null || map.isEmpty())
            return "";

        Set<String> excludeMap = new HashSet<String>();
        if (excludeKeys != null && excludeKeys.length > 0)
            excludeMap.addAll(Arrays.asList(excludeKeys));

        List<String> list = new ArrayList<String>();
        for (String key : map.keySet()) {
            if (excludeMap.contains(key)) continue;
            String val = map.get(key);
            if (val == null || val.isEmpty()) continue;
            list.add(key + "=" + val);
        }

        Collections.sort(list);

        String packVal = Joiner.on(sep).join(list);
        log.info("{} packVal={}", LOG_TAG, packVal);
        return packVal;
    }

    /**
     * 微信参数打包
     *
     * @param arr List
     * @param sep String
     * @return String
     */
    public static String packValue(List<String> arr, String sep) {
        return packValue(arr, sep, null);
    }


    /**
     * 微信参数打包
     *
     * @param arr      List
     * @param sep      String
     * @param excludes String[]
     * @return String
     */
    public static String packValue(List<String> arr, String sep, String[] excludes) {
        if (arr == null || arr.isEmpty())
            return "";

        Set<String> excludeMap = new HashSet<String>();
        if (excludes != null && excludes.length > 0)
            excludeMap.addAll(Arrays.asList(excludes));

        List<String> list = new ArrayList<String>();
        for (String item : arr) {
            if (item != null && !item.isEmpty()) {
                if (excludeMap.contains(item)) continue;
                list.add(item);
            }
        }

        Collections.sort(list);

        String packVal = Joiner.on(sep).join(list);
        log.info("{} packVal={}", LOG_TAG, packVal);
        return packVal;
    }

}
