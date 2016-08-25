package com.xdkj.campus.menu.ui.dishdiscount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.DishesDiscountSwitchFragmentAdapter;
import com.xdkj.campus.menu.adapter.DishesSwitchFragmentAdapter;
import com.xdkj.campus.menu.base.BaseBackFragment;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishesDishcountSwitchFragment extends BaseBackFragment
{
    private TabLayout mTab;
    private ViewPager mViewPager;
    private static String title;

    public static DishesDishcountSwitchFragment newInstance(String string)
    {
        title = string;
        Bundle args = new Bundle();

        DishesDishcountSwitchFragment fragment = new DishesDishcountSwitchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.wechat_fragment_tab_second, container, false);
        initView(view);
        return attachToSwipeBack(view);
    }

    private void initView(View view)
    {
        ((TextView) view.findViewById(R.id.title_middle)).setText(title);
        ((LinearLayout) view.findViewById(R.id.title_ll_left)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _mActivity.onBackPressed();
            }
        });
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab().setText("全部"));
        mTab.addTab(mTab.newTab().setText("陌生人"));

        mViewPager.setAdapter(new DishesDiscountSwitchFragmentAdapter(
                getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}
