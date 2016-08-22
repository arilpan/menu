package com.xdkj.campus.menu.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.ui.place.SelectDishListFragment;
import com.xdkj.campus.menu.ui.place.SelectPlaceFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * 接受菜品id,并且可以进入预约页面
 */
public class DishDetailFragment extends BaseFragment
{
    public static int dish_id;

    public DishDetailFragment()
    {
    }

    public static DishDetailFragment newInstance(int id)
    {
        dish_id = id;
        Bundle args = new Bundle();
        DishDetailFragment fragment = new DishDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_detail, container, false);
        initView(view);
        return view;
    }

    public void gotoSubscribe()
    {
        EventBus.getDefault().post(
                new StartBrotherEvent(SelectPlaceFragment.newInstance(dish_id)));
    }

    private void initView(View view)
    {
        Button btn_subscribe = (Button) view.findViewById(R.id.btn_subscribe);
        btn_subscribe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gotoSubscribe();
            }
        });
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
