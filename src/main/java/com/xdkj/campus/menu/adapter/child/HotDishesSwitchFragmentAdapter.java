package com.xdkj.campus.menu.adapter.child;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xdkj.campus.menu.ui.dishhot.HotDishesFragment;
import com.xdkj.campus.menu.ui.dishhot.HotDishesSwitchFragment;
import com.xdkj.campus.menu.ui.good_dishes.WaterFallDishesFragment;


/**
 * Created by arilpan@qq.com on 16/8.
 */
public class HotDishesSwitchFragmentAdapter extends FragmentPagerAdapter
{
    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};

    public HotDishesSwitchFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            return HotDishesFragment.newInstance();
        } else
        {
            return HotDishesFragment.newInstance();
        }
    }

    @Override
    public int getCount()
    {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTab[position];
    }
}
