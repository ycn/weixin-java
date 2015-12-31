package cc.ycn.common.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created by andy on 6/9/15.
 */
public class StringTool {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final java.util.Random RANDOM = new java.util.Random();
    private static final Pattern R_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    private static final Pattern R_PHONE = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
    private static final Pattern R_PHONE_CODE = Pattern.compile("^\\d{4}$");
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};


    public static String formatString(String format, Object... arguments) {
        return String.format(format.replaceAll("\\{\\}", "%s"), arguments);
    }

    public static String formatStringList(String message, List<?> arguments, String delimiter) {
        int size = arguments.size();
        if (size <= 1) {
            return message + arguments.get(0).toString();
        }

        int i;
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        for (i = 0; i < size; i++) {
            if (i > 0)
                builder.append(delimiter);
            builder.append(arguments.get(i).toString());
        }
        return builder.toString();
    }

    public static String stringMultiply(String origin, int size) {
        if (size <= 1) {
            return origin;
        }

        int i;
        StringBuilder builder = new StringBuilder();
        for (i = 0; i < size; i++) {
            builder.append(origin);
        }
        return builder.toString();
    }


    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5(String origin, int length) {
        String result = "";
        try {
            result = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(result.getBytes("UTF-8"));
            result = byteArrayToHexString(md.digest());
        } catch (Exception ignore) {
        }

        if (length >= 32) return result;

        int start = (int) Math.ceil((32 - length) / 2);
        return result.substring(start, start + length);
    }

    public static String MD5(String origin) {
        return MD5(origin, 32);
    }

    public static String SHA1(String origin) {
        String result = "";
        try {
            result = DigestUtils.sha1Hex(origin);
        } catch (Exception ignore) {
        }
        return result;
    }

    public static String checkSum(String content) {
        if (content == null || content.isEmpty())
            return "";
        return checkSum(content.getBytes()) + "";
    }

    public static String checkSum(Object obj) {
        if (obj == null)
            return "";
        String content = JsonConverter.pojo2json(obj);
        return checkSum(content.getBytes()) + "";
    }

    public static String checkSum(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return "";
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue() + "";
    }

    public static boolean isEmpty(String test) {
        if (test == null || test.isEmpty())
            return true;
        return false;
    }

    public static boolean checkEmail(String test) {
        if (isEmpty(test)) return false;
        if (test.length() < 6) return false;
        if (!test.contains("@")) return false;

        boolean flag = false;
        try {
            Matcher matcher = R_EMAIL.matcher(test);
            flag = matcher.matches();
        } catch (Exception ignore) {
        }
        return flag;
    }

    public static boolean checkPhone(String test) {
        if (isEmpty(test)) return false;
        if (test.length() != 11) return false;
        if (!test.startsWith("1")) return false;

        boolean flag = false;
        try {
            Matcher matcher = R_PHONE.matcher(test);
            flag = matcher.matches();
        } catch (Exception ignore) {
        }
        return flag;
    }

    public static boolean checkPhoneVerificationCode(String test) {
        if (isEmpty(test)) return false;
        if (test.length() != 4) return false;

        boolean flag = false;
        try {
            Matcher matcher = R_PHONE_CODE.matcher(test);
            flag = matcher.matches();
        } catch (Exception ignore) {
        }
        return flag;
    }

    public static String secretPhone(String phone) {
        String secretPhone = "";
        if (phone == null) return secretPhone;

        if (phone.length() < 11) return secretPhone;

        secretPhone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());

        return secretPhone;
    }

    public static String getString(Object obj) {
        if (obj == null)
            return "";
        return obj.toString();
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStr(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = RANDOM.nextInt(RANDOM_STR.length());
            sb.append(RANDOM_STR.charAt(number));
        }
        return sb.toString();
    }

    public static long toLong(String val) {
        long result = 0;
        try {
            result = val == null ? 0 : Long.parseLong(val);
        } catch (NumberFormatException ignore) {
        }
        return result;
    }

    public static boolean strContains(String test, String list, String delimiter) {
        if (isEmpty(delimiter))
            delimiter = ",";

        if (isEmpty(list) || isEmpty(test))
            return false;

        String str = delimiter + list + delimiter;

        return str.contains(delimiter + test + delimiter);
    }
}
