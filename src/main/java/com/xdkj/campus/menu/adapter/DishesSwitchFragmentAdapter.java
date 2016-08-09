package com.xdkj.campus.menu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.xdkj.campus.menu.good_dishes.WaterFallDishesFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class DishesSwitchFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};

    public DishesSwitchFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return WaterFallDishesFragment.newInstance();
        } else {
            return WaterFallDishesFragment.newInstance();
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
