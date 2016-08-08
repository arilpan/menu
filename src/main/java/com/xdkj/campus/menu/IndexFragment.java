package com.xdkj.campus.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.xdkj.campus.menu.base.BaseLazyMainFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.good_dishes.DishesSwitchFragment;
import com.xdkj.campus.menu.ui.order.GreatDishFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class IndexFragment extends BaseLazyMainFragment {
    public IndexFragment() {
    }

    public static IndexFragment newInstance() {

        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);

//        SupportFragment supportFragment;
//        if (savedInstanceState == null) {
//            supportFragment = IndexFragment.newInstance();
//            Log.e("arilpan", "IndexFragment savedInstanceState == null ");
//            view = inflater.inflate(R.layout.fragment_mainpage, container, false);
//            Log.e("arilpan", "IndexFragment view  inflate");
//        } else {
//            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
//            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
//            supportFragment = findFragment(IndexFragment.class);
//            Log.e("arilpan", "MainFragment savedInstanceState != null ");
//        }

        initView(view);


        return view;
    }

    LinearLayout new_arrival_layout;

    private void initView(View view) {
//        EventBus.getDefault().register(this);
        ((LinearLayout) view.findViewById(R.id.pre_order_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(DishesSwitchFragment.newInstance()));
//                start(DishesSwitchFragment.newInstance(),
//                          SupportFragment.SINGLETOP);
//                EventBus.getDefault().post(new StartBrotherEvent
//                          (DishesSwitchFragment.newInstance()));
//                ((MainActivity) getParentFragment()).startBrother
//                        (new StartBrotherEvent(DishesSwitchFragment.newInstance()));
//                EventBus.getDefault().post(new StartBrotherEvent(PreOrder.newInstance()));

            }
        });
        new_arrival_layout = (LinearLayout) view.findViewById(R.id.new_arrival_layout);
        new_arrival_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start(DishesSwitchFragment.newInstance(),
//                          SupportFragment.SINGLETOP);
//                EventBus.getDefault().post(new StartBrotherEvent
//                          (DishesSwitchFragment.newInstance()));
//                ((MainActivity) getParentFragment()).startBrother
//                        (new StartBrotherEvent(DishesSwitchFragment.newInstance()));
                EventBus.getDefault().post(new StartBrotherEvent(GreatDishFragment.newInstance()));

            }
        });
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Subscribe
//    public void startBrother(StartBrotherEvent event) {
//        start(event.targetFragment, SupportFragment.SINGLETOP);
//    }

    @Override
    public void onDestroyView() {
//        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }
}
