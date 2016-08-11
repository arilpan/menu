package com.xdkj.campus.menu.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class ShopEvent {
    public SupportFragment targetFragment;

    public ShopEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
