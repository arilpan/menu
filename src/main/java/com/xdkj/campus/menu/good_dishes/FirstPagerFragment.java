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

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private PagerAdapter mAdapter;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    public static FirstPagerFragment newInstance() {

        Bundle args = new Bundle();

        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment_tab_second_pager_first, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        EventBus.getDefault().register(this);

        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

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

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder) {
                // 通知MainActivity跳转至CycleFragment
                EventBus.getDefault().post(new StartBrotherEvent(CycleFragment.newInstance(1)));
            }
        });

        // Init Datas
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String item = "联系人 " + i;
            items.add(item);
        }
        mAdapter.setDatas(items);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
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
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
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
        EventBus.getDefault().unregister(this);
    }
}
