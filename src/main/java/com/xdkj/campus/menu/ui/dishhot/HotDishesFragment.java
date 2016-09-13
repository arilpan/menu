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

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.child.HotDishPagerAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.message.APPALL;
import com.xdkj.campus.menu.base.BaseFragment;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        setTitle(view,"热门菜品");
        view.findViewById(R.id.title_ll_left).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _mActivity.onBackPressed();
            }
        });

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
                if (datas != null)
                {
                    String dish_id = datas.get(position).getDishes_id();
                    EventBus.getDefault().post(
                            new StartBrotherEvent(
                                    DishDetailFragment.newInstance(dish_id)));
                }

                // 通知MainActivity跳转
//                EventBus.getDefault().post(
//                        new StartBrotherEvent(DishDetailFragment.newInstance(1)));
            }
        });

//        // Init Datas
        List<APPALL.ValueBean.DataBean> items = new ArrayList<>();
        mAdapter.setDatas(items);

        EventBus.getDefault().post(new NetworkEvent(RequestType.INDEX_DISH_HOT,
                "36dbde58-5ab5-41b5-915c-66048e63a5df"));
    }

    @Override
    public void onRefresh()
    {
        EventBus.getDefault().post(new NetworkEvent(RequestType.INDEX_DISH_HOT,
                "36dbde58-5ab5-41b5-915c-66048e63a5df"));
        Log.e("arilpan", "HotDishesFragment onRefresh  ");
        mRefreshLayout.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1500);
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
        if (APIAddr.dish_hot_more_url.equals(event.url))
        {
            Log.e("arilpan", "HotDishFragment equals?");
            setData(getData(event.url + event.id));
        } else
        {
            Log.e("arilpan", "HotDishFragment what happend?");
        }
    }

    public List<APPALL.ValueBean.DataBean> getData(String url)
    {
        try
        {
            final JsonAdapter<APPALL>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPALL.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPALL datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            datas = datas_arry.getValue().getData();
            for (APPALL.ValueBean.DataBean data : datas)
            {
                Log.e("arilpan", data.getDiscount_type() +
                        ",code :" + data.getDishes_price());
            }
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    List<APPALL.ValueBean.DataBean> datas;

    public void setData(final List<APPALL.ValueBean.DataBean> items)
    {
        try
        {
            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mAdapter.setDatas(items);
                    //stuff that updates ui
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
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
