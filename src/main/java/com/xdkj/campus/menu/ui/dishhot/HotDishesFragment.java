package com.xdkj.campus.menu.ui.dishhot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.WaterFallPagerAdapter;
import com.xdkj.campus.menu.adapter.child.HotDishPagerAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.DishAPI;
import com.xdkj.campus.menu.api.entitiy.APIALL;
import com.xdkj.campus.menu.api.entitiy.APIDish;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class HotDishesFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private HotDishPagerAdapter mAdapter;

    public static HotDishesFragment newInstance()
    {
        Bundle args = new Bundle();
        HotDishesFragment fragment = new HotDishesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_hot_switch_layout, container, false);
        initView(view);
        return view;
    }

    /****************************************************************/

    /****************************************************************/
    private void initView(View view)
    {
        EventBus.getDefault().register(this);

        mRecy = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new HotDishPagerAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager
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
        List<APIALL.ValueBean.DataBean > items = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            APIALL.ValueBean.DataBean  item =
                    new APIALL.ValueBean.DataBean ();
            item.setDishes_price("192");
            item.setPurchase_count(9999);
            items.add(item);

        }
        mAdapter.setDatas(items);

        EventBus.getDefault().post(new NetworkEvent(RequestType.INDEX_ALL));
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


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "HotDishFragment 你调用咩?");
        if(event.url == APIAddr.dish_hot_more_url)
        {

        }
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
        EventBus.getDefault().unregister(this);
    }
}
