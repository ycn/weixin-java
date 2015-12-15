package cc.ycn.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andy on 6/9/15.
 */
public class JsonConverter {

    private final static Logger log = LoggerFactory.getLogger(JsonConverter.class);
    private final static String LOG_TAG = "[JsonConverter]";

    public static String pojo2json(Object o) {
        if (o == null) return null;
        return JSON.toJSONString(o);
    }

    public static String pojo2json(Object o, String... excludes) {
        if (o == null) return null;
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String exclude : excludes) {
            filter.getExcludes().add(exclude);
        }
        return JSON.toJSONString(o, filter);
    }

    public static <T> T json2pojo(String json, Class<T> clazz) {
        json = json == null ? "" : json.trim();
        if (json.isEmpty()) return null;
        T t = null;
        try {
            t = JSON.parseObject(json, clazz);
        } catch (Exception ignore) {
            log.warn("{} wrong format:{}", LOG_TAG, json);
        }
        return t;
    }
}
