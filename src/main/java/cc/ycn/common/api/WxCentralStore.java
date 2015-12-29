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

    public static void init(String host, int port) {
        if (instance.get() == null)
            instance.compareAndSet(null, new WxCentralStore(host, port));
    }

    private WxCentralStore(String host, int port) {
        super(host, port, 11);

        WxConfigCache.init(this, 86400, 10, 1000, 10);
        WxVerifyTicketCache.init(this, 600, 5, 10, 5);
        WxRefreshTokenCache.init(this, 7200, 10, 1000, 10);

        WxAccessTokenCache.init(this, 7200, 10, 10000, 10);
        WxJSTicketCache.init(this, 7200, 10, 10000, 10);
        WxCardTicketCache.init(this, 7200, 10, 10000, 10);
        WxPreAuthCodeCache.init(this, 1800, 5, 10, 5);

    }
}
