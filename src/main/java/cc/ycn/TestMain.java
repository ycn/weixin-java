package cc.ycn;

import cc.ycn.common.api.CentralStore;
import cc.ycn.common.bean.WxConfig;
import cc.ycn.common.bean.menu.WxMenu;
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


        WxMenu click1 = WxMenu.CLICK()
                .name("点击我")
                .key("click_1")
                .build();

        WxMenu click2 = WxMenu.CLICK()
                .name("点击我2")
                .key("click_2")
                .build();

        WxMenu menu1 = WxMenu.MENU()
                .name("菜单A")
                .addSubMenu(click1)
                .build();

        WxMenu menu2 = WxMenu.MENU()
                .name("菜单B")
                .addSubMenu(click1)
                .addSubMenu(click2)
                .build();

        WxMenu menu = WxMenu.MENU()
                .addMenu(menu1)
                .addMenu(click2)
                .addMenu(menu2)
                .build();

        System.out.println(JsonConverter.pojo2json(menu));


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
