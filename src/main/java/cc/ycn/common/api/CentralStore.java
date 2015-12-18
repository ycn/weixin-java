package cc.ycn.common.api;

/**
 * 中央仓库接口
 *
 * @author andy <andy@ycn.cc>
 */
public interface CentralStore {

    void set(String key, String value);

    void set(String key, String value, long expireSeconds);

    String get(String key);
}
