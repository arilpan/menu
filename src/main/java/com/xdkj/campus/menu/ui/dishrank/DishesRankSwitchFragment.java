package com.xdkj.campus.menu.ui.dishrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.DishesRankSwitchFragmentAdapter;
import com.xdkj.campus.menu.adapter.DishesSwitchFragmentAdapter;
import com.xdkj.campus.menu.base.BaseBackFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishesRankSwitchFragment extends BaseBackFragment
{
    private TabLayout mTab;
    //    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private static String title;

    public static DishesRankSwitchFragment newInstance(String string)
    {
        title = string;
        Bundle args = new Bundle();

        DishesRankSwitchFragment fragment = new DishesRankSwitchFragment();
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
        return view;
//        return attachToSwipeBack(view);
    }

    /**
     * @param view
     */
    private void initView(View view)
    {
        ((TextView) view.findViewById(R.id.title_middle)).setText(title);
        view.findViewById(R.id.title_ll_left).setOnClickListener(new View
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

        mTab.addTab(mTab.newTab().setText("餐厅1"));
        mTab.addTab(mTab.newTab().setText("餐厅2"));

        mViewPager.setAdapter(new DishesRankSwitchFragmentAdapter(getFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }


//    protected void initLazyView(@Nullable Bundle savedInstanceState) {
//        mViewPager.setAdapter(new DishesSwitchFragmentAdapter(getChildFragmentManager()));
//        mTab.setupWithViewPager(mViewPager);
//    }


    //    @Override
    public boolean onBackPressedSupport()
    {
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "DishesSwitchFragment on back press");
        return false;
//        return true;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}
