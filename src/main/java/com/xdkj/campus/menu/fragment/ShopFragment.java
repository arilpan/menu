package com.xdkj.campus.menu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.backup.ContentFragment;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.ui.menu.*;
import com.xdkj.campus.menu.event.ShopEvent;
import com.xdkj.campus.menu.ui.place.SelectPlaceDetailFragment;

import me.yokeyword.fragmentation.SupportFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by YoKeyword on 16/2/4.
 * Modifed by arilpan on 16/8.
 */

public class ShopFragment extends BaseFragment
{
    public static final String TAG = ShopFragment.class.getSimpleName();
    FrameLayout fl_child_list_content_container;
    private Toolbar mToolbar;

    public static ShopFragment newInstance()
    {
        Bundle args = new Bundle();
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        enqueueAction(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });

        initView(view, savedInstanceState);
        return view;
    }

    private Button order_now_btn;
    public static TextView total_price;
    FragmentManager fManager;
    FragmentTransaction transaction;


    public void setTotalPriceView()
    {
        total_price.setText("￥" + DishList.getTotalPrice());
    }

    public void gotoSelectDetail()
    {
        EventBus.getDefault().post(new StartBrotherEvent(SelectPlaceDetailFragment.newInstance(1)));
    }

    private void initView(View view, Bundle savedInstanceState)
    {
        EventBus.getDefault().register(this);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        fl_child_list_content_container = (FrameLayout) view.findViewById(R.id
                .fl_child_list_content_container);
        order_now_btn = (Button)view.findViewById(R.id.order_now_btn);
        order_now_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gotoSelectDetail();
            }
        });

        fManager = getFragmentManager();
        transaction = fManager.beginTransaction();

        mToolbar.setTitle("商店");
        initToolbarMenu(mToolbar);
        total_price = (TextView) view.findViewById(R.id.total_price);
        setTotalPriceView();
        if (savedInstanceState == null)
        {
            ArrayList<String> listMenus = new ArrayList<>();
            listMenus.add("热门推荐");
            listMenus.add("新品尝鲜");
            listMenus.add("精品套餐");
            listMenus.add("分类选择");
            ArrayList<String> hidenMenu = new ArrayList<>();
            hidenMenu.add("煲类");
            hidenMenu.add("汤");
            hidenMenu.add("小菜");
            hidenMenu.add("酒水饮料");
            hidenMenu.add("盖浇饭类");
            hidenMenu.add("炒面类");
            hidenMenu.add("拉面类");
            hidenMenu.add("盖浇面类");
            hidenMenu.add("特色菜");
            hidenMenu.add("加料");
            hidenMenu.add("馄饨类");
            hidenMenu.add("其他");

            MenuListFragment menuListFragment = MenuListFragment.newInstance(listMenus, hidenMenu);
            loadRootFragment(R.id.fl_list_container, menuListFragment);
            replaceLoadRootFragment(R.id.fl_content_container,
                    SelectFragment.newInstance("热门推荐", "1"), false);

            ((RelativeLayout) view.findViewById(R.id.menu_selected)).setOnClickListener(
                    new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (showDishList)
                            {
                                //栈-->子栈
                                SupportFragment dishListFragment = findChildFragment
                                        (DishListFragment.class);
                                if (dishListFragment != null)
                                {
                                    dishListFragment.pop();
                                } else
                                {
                                    Log.e("arilpan", "hiden程序异常");
                                }
                                showDishList = false;
                            } else
                            {
                                SupportFragment dishListFragment = findChildFragment
                                        (DishListFragment.class);
                                if (dishListFragment == null)
                                {
                                    replaceLoadRootFragment(
                                            R.id.fl_child_list_content_container,
                                            DishListFragment.newInstance("菜单列表栏目", "1")
                                            , true);
//                                switchDishListFragment(
//                                        DishListFragment.newInstance("菜单列表栏目", "1"));
                                    Log.e("arilpan", "show new dishListFragment ");
                                } else
                                {
                                    Log.e("arilpan", "show dishListFragment ");
                                    transaction.show(dishListFragment);
                                }
                                //显示已经选择的菜品
                                showDishList = true;
                            }
                            //新的菜单列表 浮动listview
                        }
                    });
        }
    }

    static boolean showDishList = false;

    @Override
    public boolean onBackPressedSupport()
    {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        Toast.makeText(_mActivity, "onBackPressedSupport-->返回false,交给上层处理!",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    public void switchSelectFragment(SelectFragment fragment)
    {
        SupportFragment selectFragment = findChildFragment(SelectFragment.class);
        if (selectFragment != null)
        {
            selectFragment.replaceFragment(fragment, false);
        }
    }

    @Subscribe
    public void setPrice(ShopEvent event)
    {
        Log.e("arilpan", "以前能收到 现在收到通知无法设置总额了");
        setTotalPriceView();
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment)
    {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null)
        {
            contentFragment.replaceFragment(fragment, false);
        }
    }

    public void switchDishListFragment()
    {
        SupportFragment dishListFragment = findChildFragment(DishListFragment.class);
        if (dishListFragment != null)
        {
            dishListFragment.pop();
        } else
        {
            Log.e("arilpan", "dishListFragment == null ");
        }
    }

    @Override
    public void onDestroyView()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
