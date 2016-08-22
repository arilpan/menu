package com.xdkj.campus.menu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xdkj.campus.menu.ui.dishhot.HotDishesSwitchFragment;
import com.xdkj.campus.menu.ui.good_dishes.WaterFallDishesFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class HotDishesSwitchFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};

    public HotDishesSwitchFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HotDishesSwitchFragment.newInstance("");
        } else {
            return HotDishesSwitchFragment.newInstance("");
        }
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
