package com.xdkj.campus.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.child.ContentFragment;
import com.xdkj.campus.menu.child.DishListFragment;
import com.xdkj.campus.menu.child.MenuListFragment;
import com.xdkj.campus.menu.child.SelectFragment;
import me.yokeyword.fragmentation.SupportFragment;

import java.util.ArrayList;

/**
 * Created by YoKeyword on 16/2/4.
 */
public class ShopFragment extends BaseFragment {
    public static final String TAG = ShopFragment.class.getSimpleName();

    private Toolbar mToolbar;

    public static ShopFragment newInstance() {
        Bundle args = new Bundle();

        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        enqueueAction(new Runnable() {
            @Override
            public void run() {

            }
        });

        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mToolbar.setTitle("商店");
        initToolbarMenu(mToolbar);

        if (savedInstanceState == null) {
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


            ((LinearLayout) view.findViewById(R.id.menu_selected)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showDishList) {
//                        popChild();
//                        pop();
                        FragmentManager fManager = getFragmentManager();
                        fManager.beginTransaction().remove(dl);
                        if (dl != null) {
                            dl.pop();
                            Log.e("arilpan","不为空 Pop");
                        } else {
                            Log.e("arilpan","为空");
                        }

//                        popToChild(DishListFragment.class, true);
                        showDishList = false;
                    } else {
                        dl = DishListFragment.newInstance("菜单列表栏目", "1");
                        replaceLoadRootFragment(R.id.fl_child_list_content_container,
                                dl, true);
                        showDishList = true;
                    }

                    //新的菜单列表 浮动listview


                }
            });
        }
    }

    DishListFragment dl;
    static boolean showDishList = false;

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        Toast.makeText(_mActivity, "onBackPressedSupport-->返回false,交给上层处理!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void switchSelectFragment(SelectFragment fragment) {
        SupportFragment selectFragment = findChildFragment(SelectFragment.class);
        if (selectFragment != null) {
            selectFragment.replaceFragment(fragment, false);
        }
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }

    }
}