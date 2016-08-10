package com.xdkj.campus.menu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.ShopFragment;
import com.xdkj.campus.menu.fragment.CycleFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by xdkj on 2016/8/4.
 */
public class Test extends SupportFragment {
    private static final String ARG_NUMBER = "arg_number";

    TextView mall_1;
    TextView mall_2;

    private int mNumber;

    public static CycleFragment newInstance(int number) {
        CycleFragment fragment = new CycleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_mall, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mall_1 = (TextView) view.findViewById(R.id.mall_1);
        mall_2 = (TextView) view.findViewById(R.id.mall_2);
        String title = "循环Fragment" + mNumber;
        mall_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start(CycleFragment.newInstance(mNumber + 1));
                start(ShopFragment.newInstance());
            }
        });
        mall_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(ShopFragment.newInstance());
//                startWithPop(CycleFragment.newInstance(mNumber + 1));
            }
        });
    }
}
