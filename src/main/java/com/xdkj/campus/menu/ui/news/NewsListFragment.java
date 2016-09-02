package com.xdkj.campus.menu.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.NewsListAdapter;
import com.xdkj.campus.menu.api.message.APPNewsList;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private NewsListAdapter mAdapter;

    public static NewsListFragment newInstance()
    {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_news, container, false);
        initView(view);
        return view;
    }

    /****************************************************************/

    /****************************************************************/
    private void initView(View view)
    {
        ((TextView) view.findViewById(R.id.title_middle)).setText("新闻资讯");
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

        mRecy = (RecyclerView) view.findViewById(R.id.news_recyview);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new NewsListAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager
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
        // Init Datas
//        final List<News> items = new ArrayList<>();
//        for (int i = 0; i < 20; i++)
//        {
//            if (i == 0)
//            {
//                News item = new News(1,
//                        "习近平：让“一带一路”建设造福沿线各国人民",
//                        "2016-01-01 09:00:00",
//                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
//                items.add(item);
//            }
//
//        } mAdapter.setDatas(items);
        //点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder)
            {
                Log.e("arilpan", " NewsListAdapter onItemClick  ");
                // 通知MainActivity跳转至CycleFragment
                EventBus.getDefault().post(
                        new StartBrotherEvent(
                                DishNewsDetailFragment.newInstance(
                                        datas.get(position).getNews_id())));
            }
        });


        EventBus.getDefault().post(new NetworkEvent(RequestType.NEWS_LIST));

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
        }, 1500);
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "NewsListFragment哥 你调用咩?");
        if (RequestType.NEWS_LIST == event.reqType)
        {
            setData(getData(event));
        }

    }

    public List<APPNewsList.ValueBean.ListBean.DataBean> getData(NetworkEvent event)
    {
        try
        {
            final JsonAdapter<APPNewsList>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPNewsList.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(event.url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPNewsList datas_arry = COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            datas = datas_arry.getValue().getList().getData();
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    List<APPNewsList.ValueBean.ListBean.DataBean> datas;

    public void setData(final List<APPNewsList.ValueBean.ListBean.DataBean> items)
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
    /**
     * Reselected Tab
     */
//    @Subscribe
//    public void onTabSelectedEvent(TabSelectedEvent event)
//    {
//        if (event.position != SECOND)
//            return;
//        if (mInAtTop)
//        {
//            mRefreshLayout.setRefreshing(true);
//            onRefresh();
//        } else
//        {
//            scrollToTop();
//        }
//    }
//
//    @Subscribe
//    public void start(StartBrotherEvent event)
//    {
//        start(event.targetFragment);
//    }
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
