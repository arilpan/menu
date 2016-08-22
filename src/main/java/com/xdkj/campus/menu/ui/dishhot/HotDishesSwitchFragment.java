package com.xdkj.campus.menu.ui.dishhot;

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
import com.xdkj.campus.menu.adapter.DishesSwitchFragmentAdapter;
import com.xdkj.campus.menu.adapter.HotDishesSwitchFragmentAdapter;
import com.xdkj.campus.menu.base.BaseBackFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class HotDishesSwitchFragment extends BaseBackFragment {
    private TabLayout mTab;
    //    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private static String title;

    public static HotDishesSwitchFragment newInstance(String string) {
        title = string;
        Bundle args = new Bundle();

        HotDishesSwitchFragment fragment = new HotDishesSwitchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment_tab_second, container, false);
        initView(view);
        return attachToSwipeBack(view);
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.title_middle)).setText(title);
        ((LinearLayout) view.findViewById(R.id.title_ll_left)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
//        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
//        mToolbar.setTitle("联系人");
//        initToolbarMenu(mToolbar);

        mTab.addTab(mTab.newTab().setText("全部"));
        mTab.addTab(mTab.newTab().setText("陌生人"));

        mViewPager.setAdapter(new HotDishesSwitchFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }


//    protected void initLazyView(@Nullable Bundle savedInstanceState) {
//        mViewPager.setAdapter(new DishesSwitchFragmentAdapter(getChildFragmentManager()));
//        mTab.setupWithViewPager(mViewPager);
//    }


    //    @Override
//    public boolean onBackPressedSupport() {
//        // 这里实际项目中推荐使用 EventBus接耦
////        ((TestOne) getParentFragment()).onBackToFirstFragment();
//        Log.e("arilpan","DishesSwitchFragment on back press");
//        return false;
////        return true;
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
