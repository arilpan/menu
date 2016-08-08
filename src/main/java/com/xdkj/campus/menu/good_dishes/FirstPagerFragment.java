package com.xdkj.campus.menu.good_dishes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.PagerAdapter;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.fragment.CycleFragment;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class FirstPagerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    int SECOND = 1;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private PagerAdapter mAdapter;

    private boolean mInAtTop_right = true;
    private int mScrollTotal_right;
    private SwipeRefreshLayout mRefreshLayout_right;
    private RecyclerView mRecy_right;
    private PagerAdapter mAdapter_right;

    public static FirstPagerFragment newInstance() {
        Bundle args = new Bundle();
        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_switch_layout, container, false);
        initView(view);
        return view;
    }
    /****************************************************************/
    private void initRight(View view){
        mRecy_right = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout_right = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout_right.setOnRefreshListener(this);
        mAdapter_right = new PagerAdapter(_mActivity);
        mRecy_right.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy_right.setLayoutManager(manager);
        mRecy_right.setAdapter(mAdapter_right);

        mRecy_right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal_right += dy;
                if (mScrollTotal_right <= 0) {
                    mInAtTop_right = true;
                } else {
                    mInAtTop_right = false;
                }
            }
        });

        //点击事件
        mAdapter_right.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder) {
                // 通知MainActivity跳转至CycleFragment
                EventBus.getDefault().post(new StartBrotherEvent(CycleFragment.newInstance(1)));
            }
        });

        // Init Datas
        List<Dish> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Dish item = new Dish("粉蒸肉" + i, "123123123123jasfkldiwoe jklsanfl dkn", "￥22");
            items.add(item);
        }
        mAdapter_right.setDatas(items);
    }
    /****************************************************************/
    private void initView(View view) {
        EventBus.getDefault().register(this);

        mRecy = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new PagerAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });

        //点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder) {
                // 通知MainActivity跳转至CycleFragment
                EventBus.getDefault().post(new StartBrotherEvent(CycleFragment.newInstance(1)));
            }
        });

        // Init Datas
        List<Dish> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Dish item = new Dish("粉蒸肉" + i, "123123123123jasfkldiwoe jklsanfl dkn", "￥22");
            items.add(item);
        }
        mAdapter.setDatas(items);

        initRight(view);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);

        mRefreshLayout_right.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout_right.setRefreshing(false);
            }
        }, 2500);
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != SECOND) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }

        if (mInAtTop_right) {
            mRefreshLayout_right.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
        mRecy_right.smoothScrollToPosition(0);
    }

    @Override
    public boolean onBackPressedSupport() {
        // 默认flase，继续向上传递
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        mRecy_right.setAdapter(null);

        EventBus.getDefault().unregister(this);
    }
}
