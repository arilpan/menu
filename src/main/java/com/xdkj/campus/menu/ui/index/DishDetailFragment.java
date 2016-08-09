package com.xdkj.campus.menu.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;


public class DishDetailFragment extends BaseFragment {
    public static int dish_id;

    public DishDetailFragment() {
    }

    public static DishDetailFragment newInstance(int id) {
        dish_id = id;
        Bundle args = new Bundle();
        DishDetailFragment fragment = new DishDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_detail, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
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
