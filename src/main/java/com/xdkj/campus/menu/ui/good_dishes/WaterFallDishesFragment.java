package com.xdkj.campus.menu.ui.good_dishes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.WaterFallPagerAdapter;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class WaterFallDishesFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private WaterFallPagerAdapter mAdapter;

    public static WaterFallDishesFragment newInstance()
    {
        Bundle args = new Bundle();
        WaterFallDishesFragment fragment = new WaterFallDishesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_switch_layout, container, false);
        initView(view);
        return view;
    }

    /****************************************************************/

    /****************************************************************/
    private void initView(View view)
    {
//        EventBus.getDefault().register(this);

        mRecy = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new WaterFallPagerAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
                .VERTICAL));
        mRecy.setAdapter(mAdapter);

        //滑动事件
        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0)
                {
                    mInAtTop = true;
                } else
                {
                    mInAtTop = false;
                }
            }
        });

        //点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder)
            {
//                if (DishDetailFragment.getInstance().isAdded()) {
//                    DishDetailFragment.getInstance().show(fm, tag);
//                } else {
//
//                }

                // 通知MainActivity跳转至CycleFragment
                EventBus.getDefault().post(
                        new StartBrotherEvent(DishDetailFragment.newInstance(1)));
            }
        });

        // Init Datas
        List<Dish> items = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            if (i == 0)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字加上五个字共是十五字", "￥22");
                items.add(item);
            } else if (i == 1)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字加上五个字共是", "￥16");
                items.add(item);
            } else if (i == 2)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字加上五个字共是十四", "￥32");
                items.add(item);
            } else if (i == 3)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字", "￥17");
                items.add(item);
            } else if (i == 5)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字这是五个字这是五个字这是五个字五五二十五", "￥32");
                items.add(item);
            } else if (i == 6)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字这是五个字这是五个字这是五个字五五二十五再加五个字", "￥32");
                items.add(item);
            } else if (i == 7)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字这是五个字这是五个字这是五个字五五二十五再加五个字再加五个字", "￥32");
                items.add(item);
            } else if (i == 8)
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字这是五个字这是五个字这是五个字五五二十五再加五个字再加五个字再加五个字", "￥32");
                items.add(item);
            } else
            {
                Dish item = new Dish("粉蒸肉L" + i, "这是五个字加上五个字共是十五字", "￥86");
                items.add(item);
            }

        }
        mAdapter.setDatas(items);
    }

    @Override
    public void onRefresh()
    {
        mRefreshLayout.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event)
    {
        if (event.position != SECOND)
            return;
        if (mInAtTop)
        {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else
        {
            scrollToTop();
        }
    }
    @Subscribe
    public void start(StartBrotherEvent event)
    {
        start(event.targetFragment);
    }

    private void scrollToTop()
    {
        mRecy.smoothScrollToPosition(0);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        // 默认flase，继续向上传递
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mRecy.setAdapter(null);
//        EventBus.getDefault().unregister(this);
    }
}
