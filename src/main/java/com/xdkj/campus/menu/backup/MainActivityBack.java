package com.xdkj.campus.menu.backup;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.fragment.DiscountFragment;
import com.xdkj.campus.menu.fragment.IndexFragment;
import com.xdkj.campus.menu.fragment.MineFragment;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.fragment.ShopFragment;
import com.xdkj.campus.menu.ZhihuThirdFragment;
import com.xdkj.campus.menu.base.BaseLazyMainFragment;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.view.BottomBar;
import com.xdkj.campus.menu.view.BottomBarTab;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import org.greenrobot.eventbus.EventBus;

/**
 * 类知乎 复杂嵌套Demo tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/6/2.
 */
public class MainActivityBack extends SupportActivity
        implements BaseLazyMainFragment.OnBackToFirstListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_main);
//        EventBus.getDefault().register(this);

        if (savedInstanceState == null) {
            mFragments[FIRST] = IndexFragment.newInstance();
            mFragments[SECOND] = DiscountFragment.newInstance(APIAddr.shop_one_id);
            mFragments[THIRD] = ZhihuThirdFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(IndexFragment.class);
            mFragments[SECOND] = findFragment(DiscountFragment.class);
            mFragments[THIRD] = findFragment(ZhihuThirdFragment.class);
            mFragments[FOURTH] = findFragment(MineFragment.class);
        }

        initView();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_home_white_24dp,"首页"))
                .addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp,"优惠"))
                .addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp,"留言-点餐"))
                .addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp,"我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof IndexFragment) {
                        currentFragment.popToChild(IndexFragment.class, false);
                    } else if (currentFragment instanceof DiscountFragment) {
                        currentFragment.popToChild(DiscountFragment.class, false);
                    } else if (currentFragment instanceof ZhihuThirdFragment) {
                        currentFragment.popToChild(ShopFragment.class, false);
                    } else if (currentFragment instanceof MineFragment) {
                        currentFragment.popToChild(MineFragment.class, false);
                    }
                    return;
                }


                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }



    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
//    @Subscribe
//    public void onHiddenBottombarEvent(boolean hidden) {
//        if (hidden) {
//            mBottomBar.hide();
//        } else {
//            mBottomBar.show();
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
