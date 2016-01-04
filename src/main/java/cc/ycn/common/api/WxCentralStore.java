package cc.ycn.common.api;

import cc.ycn.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by andy on 12/29/15.
 */
public class WxCentralStore extends DefaultRedisCentralStore {

    private final static Logger log = LoggerFactory.getLogger(WxCentralStore.class);
    private final static String LOG_TAG = "[WxCentralStore]";
    private static final AtomicReference<WxCentralStore> instance = new AtomicReference<WxCentralStore>();

    /**
     * 初始化为子系统
     *
     * @param host String
     * @param port int
     */
    public static void init(String host, int port) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCentralStore(host, port));
    }

    /**
     * 初始化为中控系统
     * (请自行保证中控系统的单例性)
     *
     * @param host String
     * @param port int
     */
    public static void initAdmin(String host, int port) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCentralStore(host, port, false));
    }

    private WxCentralStore(String host, int port) {
        super(host, port, 11);

        WxConfigCache.init(this, 600, 10, 1000, 10, true);
        WxVerifyTicketCache.init(this, 600, 5, 10, 5, true);
        WxRefreshTokenCache.init(this, 600, 10, 1000, 10, true);

        WxAccessTokenCache.init(this, 600, 10, 10000, 10, true);
        WxJSTicketCache.init(this, 600, 10, 10000, 10, true);
        WxCardTicketCache.init(this, 600, 10, 10000, 10, true);
        WxPreAuthCodeCache.init(this, 600, 5, 10, 5, true);
    }

    private WxCentralStore(String host, int port, boolean readonly) {
        super(host, port, 11);

        WxConfigCache.init(this, 86400, 10, 1000, 10, readonly);
        WxVerifyTicketCache.init(this, 600, 5, 10, 5, readonly);
        WxRefreshTokenCache.init(this, 7200, 10, 1000, 10, readonly);

        WxAccessTokenCache.init(this, 7200, 10, 10000, 10, readonly);
        WxJSTicketCache.init(this, 7200, 10, 10000, 10, readonly);
        WxCardTicketCache.init(this, 7200, 10, 10000, 10, readonly);
        WxPreAuthCodeCache.init(this, 1800, 5, 10, 5, readonly);

    }
}
