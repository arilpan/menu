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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.NewsListAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.message.APPNewsList;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class NewsListFragment extends BaseFragment
        implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;

    private BGABanner news_banner;
    List<String> imgs;


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

        news_banner = (BGABanner) view.findViewById(R.id.news_banner);
        news_banner.setAdapter(new BGABanner.Adapter()
        {
            @Override
            public void fillBannerItem(BGABanner banner,
                                       View view,
                                       Object model,
                                       int position)
            {
                Log.e("arilpan", "model to string " + model.toString());
                Glide.with(view.getContext())
                        .load(APIAddr.BASE_IMG_URL + model.toString())
                        .error(R.drawable.preferential_list_item_zanwutupian)
                        .into((ImageView) view);
            }
        });


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
            Log.e("arilpan", "新闻列表url:" + event.url);
            setData(getData(event));
        }

    }

    public List<APPNewsList.ValueBean.ListBean.DataBean> getData(NetworkEvent event)
    {
        ResponseBody body = null;
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
            body = response.body();

            APPNewsList datas_arry = COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            datas = datas_arry.getValue().getList().getData();

            imgs = new ArrayList<>();
            imgs.add(datas_arry.getValue().getUrl1());
            imgs.add(datas_arry.getValue().getUrl2());
            imgs.add(datas_arry.getValue().getUrl3());

            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            body.close();
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
                    if (items != null)
                    {
                        mAdapter.setDatas(items);
                        news_banner.setData(imgs, null);
                    }
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
