package com.xdkj.campus.menu.ui.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.backup.CycleFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Create/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class SelectPlaceDetailFragment extends SupportFragment
{
    private static final String ARG_NUMBER = "arg_number";
    private int mNumber;

    public static SelectPlaceDetailFragment newInstance(int number)
    {
        SelectPlaceDetailFragment fragment = new SelectPlaceDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);
        initView(view);
        return view;
    }

    //pre_order_list_small_rl
    // 用餐人数
    // 用餐时间
    // 包间
    // 选择菜品
    RelativeLayout orderPersonNumRl;
    RelativeLayout orderTimeRl;
    RelativeLayout orderRoomRl;
    RelativeLayout orderSelectDishRl;


    private void initView(View view)
    {
        orderPersonNumRl = (RelativeLayout) view.findViewById(R.id.order_person_num);
        orderTimeRl = (RelativeLayout) view.findViewById(R.id.order_time);
        orderRoomRl = (RelativeLayout) view.findViewById(R.id.order_room);
        orderSelectDishRl = (RelativeLayout) view.findViewById(R.id.order_dishes);

        orderSelectDishRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择菜品
            }
        });
        orderRoomRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择房间
            }
        });
        orderPersonNumRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用填写人数
            }
        });
        orderTimeRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择时间
            }
        });

    }
}
