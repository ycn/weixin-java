package cc.ycn;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.message.WxArticle;
import cc.ycn.common.bean.message.WxMessage;
import cc.ycn.common.cache.WxAccessTokenCache;
import cc.ycn.common.cache.WxConfigCache;
import cc.ycn.common.constant.WxApiType;
import cc.ycn.common.util.JsonConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 12/11/15.
 */
public class TestMain {

    private final static String MP_APP_ID = "wx81fff808e176b36a";
    private final static String MP_APP_SECRET = "c47164d8bbb97b4ee7fc7614fcbd2812";
    private final static String CP_APP_ID = "wx11e679eaf2cd4080";
    private final static String CP_APP_SECRET = "kW7YGESC8xxHC0rMWjhUEcBatqpooxxcCFneassKBczcGP27HGWBvMmR14n6eK5E";


    public static void main(String[] args) throws InterruptedException {

        WxConfigCache.init(new CentralStore() {

            private Map<String, String> map = new HashMap<String, String>();

            @Override
            public void set(String key, String value) {
                map.put(key, value);
            }

            @Override
            public void set(String key, String value, long expireSeconds) {
                map.put(key, value);
            }

            @Override
            public String get(String key) {

                switch (key) {
                    case MP_APP_ID:
                        WxConfig mpConfig = new WxConfig(MP_APP_ID, WxApiType.MP);
                        mpConfig.setAppSecret(MP_APP_SECRET);
                        return JsonConverter.pojo2json(mpConfig);
                    case CP_APP_ID:
                        WxConfig cpConfig = new WxConfig(CP_APP_ID, WxApiType.CP);
                        cpConfig.setAppSecret(CP_APP_SECRET);
                        return JsonConverter.pojo2json(cpConfig);
                }

                return map.get(key);
            }
        }, 7000);

        WxAccessTokenCache.init(new CentralStore() {

            private Map<String, String> map = new HashMap<String, String>();

            @Override
            public void set(String key, String value) {
                map.put(key, value);
            }

            @Override
            public void set(String key, String value, long expireSeconds) {
                map.put(key, value);
            }

            @Override
            public String get(String key) {
                return map.get(key);
            }
        }, 5);

        WxArticle article1 = new WxArticle();
        article1.setTitle("123");
        WxArticle article2 = new WxArticle();
        article2.setDescription("hhh");
        WxMessage msg = WxMessage.NEWS()
                .toUser("andy")
                .addArticle(article1)
                .addArticle(article2)
                .addArticle(article1)
                .kfAccount("andy@khw")
                .build();

        System.out.println(JsonConverter.pojo2json(msg));


//        WxMpServiceImpl wxMpService = new WxMpServiceImpl(MP_APP_ID);
//        System.out.println(wxMpService.getAccessToken());
//        System.out.println(wxMpService.getAccessToken());
//        Thread.sleep(5500);
//        System.out.println(wxMpService.getAccessToken());
//        System.out.println(wxMpService.getAccessToken());
//
//        WxCpServiceImpl wxCpService = new WxCpServiceImpl(CP_APP_ID);
//        System.out.println(wxCpService.getAccessToken());
//        System.out.println(wxCpService.getAccessToken());
//        Thread.sleep(5500);
//        System.out.println(wxCpService.getAccessToken());
//        System.out.println(wxCpService.getAccessToken());

    }

}
