package com.xdkj.campus.menu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.child.HotDishPagerAdapter;
import com.xdkj.campus.menu.base.BaseLazyMainFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.dishdiscount.DishesDishcountSwitchFragment;
import com.xdkj.campus.menu.ui.dishhot.HotDishesFragment;
import com.xdkj.campus.menu.ui.dishrank.DishesRankSwitchFragment;
import com.xdkj.campus.menu.ui.good_dishes.DishesSwitchFragment;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;
import com.xdkj.campus.menu.ui.news.NewsListFragment;
import com.xdkj.campus.menu.ui.order.SelectPlaceFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class IndexFragment extends BaseLazyMainFragment
{
    public IndexFragment()
    {
    }

    public static IndexFragment newInstance()
    {

        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);

//        SupportFragment supportFragment;
//        if (savedInstanceState == null) {
//            supportFragment = IndexFragment.newInstance();
//            Log.e("arilpan", "IndexFragment savedInstanceState == null ");
//            view = inflater.inflate(R.layout.fragment_mainpage, container, false);
//            Log.e("arilpan", "IndexFragment view  inflate");
//        } else {
//            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
//            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找
// (效率更高些),用下面的方法查找更方便些
//            supportFragment = findFragment(IndexFragment.class);
//            Log.e("arilpan", "MainFragment savedInstanceState != null ");
//        }

        initView(view);


        return view;
    }

    private void addBtn(View view)
    {
        ((LinearLayout) view.findViewById(R.id.pre_order_layout)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(SelectPlaceFragment.newInstance
                        ()));
            }
        });
        ((LinearLayout) view.findViewById(R.id.greate_package_layout)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(
                        //TODO:1
                        DishesSwitchFragment.newInstance("精品套餐")));
            }
        });
        ((LinearLayout) view.findViewById(R.id.recharge_layout)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(DiscountFragment.newInstance()));
            }
        });

        ((RelativeLayout) view.findViewById(R.id.dish_rank_rl)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(
                        DishesRankSwitchFragment.newInstance("美食排行")));
            }
        });
        ((RelativeLayout) view.findViewById(R.id.discount_dish_rl)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(
                        DishesDishcountSwitchFragment.newInstance("超值折扣菜")));
            }
        });
        ((RelativeLayout) view.findViewById(R.id.recharge_rl)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(DiscountFragment.newInstance()));
            }
        });
        ((RelativeLayout) view.findViewById(R.id.dayly_welfare_rl)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(NewsListFragment.newInstance()));
            }
        });

        ((RelativeLayout) view.findViewById(R.id.more_hot_dish)).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new StartBrotherEvent(HotDishesFragment.newInstance()));
            }
        });
    }

    private void dishList()
    {
        mAdapter = new HotDishPagerAdapter(_mActivity);
        index_list.setHasFixedSize(true);
        index_list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager
                .VERTICAL));
        index_list.setAdapter(mAdapter);
        Log.e("arilpan", "init index_list hot dishes");
        updateData();

        mAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View view,
                                    RecyclerView.ViewHolder holder)
            {
                EventBus.getDefault().post(
                        new StartBrotherEvent(
                                DishDetailFragment.newInstance(1)));
            }
        });

    }

    private void updateData()
    {
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
        Log.e("arilpan", "set data of  index_list hot dishes");
    }

    private void initView(View view)
    {
        index_list = (RecyclerView) view.findViewById(R.id.index_list);
        index_list.setFocusable(false);
        index_banner = (BGABanner) view.findViewById(R.id.index_banner);
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getContext(),
                R.drawable.index_banner_default));
        views.add(BGABannerUtil.getItemImageView(getContext(),
                R.drawable.index_banner_default));
        views.add(BGABannerUtil.getItemImageView(getContext(),
                R.drawable.index_banner_default));
        views.add(BGABannerUtil.getItemImageView(getContext(),
                R.drawable.index_banner_default));
        index_banner.setData(views);
        index_banner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                Log.i("arilpan",
                        "onPageScrolled  点击了第" + (position + 1) + "个banner");
            }
        });
        index_banner.setOnItemClickListener(
                new BGABanner.OnItemClickListener()
                {
                    @Override
                    public void onBannerItemClick(BGABanner banner,
                                                  View view,
                                                  Object model,
                                                  int position)
                    {
                        Log.i("arilpan",
                                "点击了第" + (position + 1) + "个banner");
                    }
                });
        index_banner.setVerticalScrollbarPosition(10);
        dishList();

        addBtn(view);

        //EventBus.getDefault().register(this);

        new_arrival_layout = (LinearLayout) view.findViewById(R.id.new_arrival_layout);
        new_arrival_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                start(DishesSwitchFragment.newInstance(),
//                          SupportFragment.SINGLETOP);
//                EventBus.getDefault().post(new StartBrotherEvent
//                          (DishesSwitchFragment.newInstance()));
//                ((MainActivity) getParentFragment()).startBrother
//                        (new StartBrotherEvent(DishesSwitchFragment.newInstance()));
                EventBus.getDefault().post(new StartBrotherEvent(DishesSwitchFragment.newInstance
                        ("新品尝鲜")));

            }
        });


    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressedSupport()
    {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }

    private BGABanner index_banner;

    private LinearLayout new_arrival_layout;
    private RecyclerView index_list;
    private HotDishPagerAdapter mAdapter;
}
