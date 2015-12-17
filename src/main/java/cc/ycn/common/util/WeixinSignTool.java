package cc.ycn.common.util;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;

import java.util.*;

/**
 * 微信签名工具
 *
 * @author andy
 */
public class WeixinSignTool {

    public static String createJSSignature(String packValue) {
        return StringTool.SHA1(packValue);
    }

    public static String createCardSignature(String packValue) {
        return StringTool.SHA1(packValue);
    }

    public static String packValue(Object obj, String sep) {
        return packValue(obj, sep, null);
    }

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
            if (part.contains("<null>")) continue;
            list.add(part);
        }

        Collections.sort(list);

        return Joiner.on(sep).join(list);
    }

    public static String packValue(Map<String, String> map, String sep) {
        return packValue(map, sep, null);
    }

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

        return Joiner.on(sep).join(list);
    }

    public static String packValue(List<String> arr, String sep) {
        return packValue(arr, sep, null);
    }

    public static String packValue(List<String> arr, String sep, String[] excludes) {
        if (arr == null || arr.isEmpty())
            return "";

        Set<String> excludeMap = new HashSet<String>();
        if (excludes != null && excludes.length > 0)
            excludeMap.addAll(Arrays.asList(excludes));

        List<String> list = new ArrayList<String>();
        for (String item : arr) {
            if (item != null && item.contains("=")) {
                String[] parts = item.split("=");
                if (excludeMap.contains(parts[0])) continue;
                String val = parts[1];
                if (val == null || val.isEmpty()) continue;
            } else {
                if (item == null || item.isEmpty()) continue;
                if (excludeMap.contains(item)) continue;
            }
            list.add(item);
        }

        Collections.sort(list);

        return Joiner.on(sep).join(list);
    }
}
