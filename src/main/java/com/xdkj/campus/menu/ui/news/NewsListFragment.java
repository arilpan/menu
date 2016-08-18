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

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.NewsListAdapter;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.News;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKeyword on 16/6/30.
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
//        EventBus.getDefault().register(this);

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
        final List<News> items = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            if (i == 0)
            {
                News item = new News(1,
                        "习近平：让“一带一路”建设造福沿线各国人民",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else if (i == 1)
            {
                News item = new News(1,
                        "李克强：集众思以建真言 汇众智以谋良策",
                        "2016-01-01 09:00:00",
                        "新华社北京8月17日电 " +
                                "8月17日，中共中央政治局常委、国务院总理李克强在中南海紫光阁向新聘任的国务院参事何秀荣、石勇、何茂春、柯锦华、徐宪平、忽培元和中央文史研究馆馆员刘彭芝、葛剑雄、张胜友、王明明颁发聘书，向大家表示祝贺。\n" +
                                "\n" +
                                "　　李克强与全体参事、馆员座谈。国务院参事室负责人作了汇报，袁行霈馆员、林毅夫参事、尹成杰特约研究员等就继承发扬中华优秀传统文化、培育新动能和新经济、发展现代农业和增加农民收入等提出建议。李克强与大家深入交流。他说，新形势下做好参事室和文史馆工作，是更好实现党的统一战线方针政策与加强国家政权建设相融合的重要抓手。广大参事馆员学养深厚、视野开阔，国务院各部门对他们的研究成果和政策建议要认真研究吸纳，更好改进政府工作，提高施政水平。");
                items.add(item);
            } else if (i == 2)
            {
                News item = new News(1,
                        "美专家:中国或加速研发尖端武器以应对萨德",
                        "2016-01-01 09:00:00",
                        "[环球网综合报道]据韩联社8月17日报道，当地时间16" +
                                "日，美国市场调研机构“全球风险研究所”资深分析师伊恩·阿姆斯特朗撰文称，如果美国在韩国部署“萨德”反导系统，中国将加快部署高超音速滑翔飞行器和多弹头分导再入式飞行器等尖端武器。\n" +
                                "\n" +
                                "　　阿姆斯特朗预测说，中国最担忧的是“萨德”系统中的远距离预警雷达AN/TPY-2，因为它可以窥测到中国内陆地区。为脱离该雷达的监控，中国可能会加快研发部署HGV与MIRV。");
                items.add(item);
            } else if (i == 3)
            {
                News item = new News(1,
                        "菲总统:不与东盟谈南海 也不会蠢到向中国宣战",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else if (i == 5)
            {
                News item = new News(1,
                        "给习近平写传记 这位作者啥来头？",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else if (i == 6)
            {
                News item = new News(1,
                        "羽毛球名将辞官创业 曾是当地最年轻处级官员",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else if (i == 7)
            {
                News item = new News(1,
                        "波兰开挖纳粹黄金列车 纳粹世纪之谜有望解开",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else if (i == 8)
            {
                News item = new News(1,
                        "LiFi把每一个灯泡都变成高速WiFi",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            } else
            {
                News item = new News(1,
                        "萨德真的让韩国更安全了吗？",
                        "2016-01-01 09:00:00",
                        "中共中央总书记、国家主席、中央军委主席习近平17日在北京人民大会堂出席推进“一带一路”建设工作座谈会并发表重要讲话强调，总结经验、坚定信心、扎实推进，聚焦政策沟通、设施联通、贸易畅通、资金融通、民心相通，聚焦构建互利合作网络、新型合作模式、多元合作平台，聚焦携手打造绿色丝绸之路、健康丝绸之路、智力丝绸之路、和平丝绸之路，以钉钉子精神抓下去，一步一步把“一带一路”建设推向前进，让“一带一路”建设造福沿线各国人民。中共中央政治局常委、国务院副总理、推进“一带一路”建设工作领导小组组长张高丽主持座谈会。 ");
                items.add(item);
            }

        }
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
                                        items.get(position))));
            }
        });
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
