package cc.ycn.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * Created by andy on 8/5/15.
 */
public class XmlConverter {

    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public static <T> String pojo2xml(T t) {
        if (t == null) return null;
        try {
            return XML_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> T xml2pojo(String xml, Class<T> type) throws IOException {
        xml = xml == null ? "" : xml.trim();
        if (xml.isEmpty()) return null;
        return XML_MAPPER.readValue(xml, type);
    }
}
