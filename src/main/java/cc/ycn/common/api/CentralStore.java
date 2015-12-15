package cc.ycn.common.api;

/**
 * Created by andy on 12/12/15.
 */
public interface CentralStore {

    void set(String key, String value);

    void set(String key, String value, long expireSeconds);

    String get(String key);
}
