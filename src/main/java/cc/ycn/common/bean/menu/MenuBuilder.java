package cc.ycn.common.bean.menu;

import java.util.ArrayList;

/**
 * Created by andy on 12/16/15.
 */
public class MenuBuilder extends BaseBuilder<MenuBuilder> {

    public MenuBuilder() {
    }

    public MenuBuilder addMenu(WxMenu menu) {
        if (menu == null)
            return this;

        if (this.menu.button == null)
            this.menu.button = new ArrayList<WxMenu>();

        this.menu.button.add(menu);

        return this;
    }

    public MenuBuilder addSubMenu(WxMenu menu) {
        if (menu == null)
            return this;

        if (this.menu.subButton == null)
            this.menu.subButton = new ArrayList<WxMenu>();

        this.menu.subButton.add(menu);

        return this;
    }

    @Override
    protected WxMenuType getType() {
        return WxMenuType.MENU;
    }

    @Override
    protected void isValid() {
    }
}
