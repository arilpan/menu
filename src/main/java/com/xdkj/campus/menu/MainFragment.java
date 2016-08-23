package com.xdkj.campus.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.fragment.DiscountFragment;
import com.xdkj.campus.menu.fragment.IndexFragment;
import com.xdkj.campus.menu.fragment.MineFragment;
import com.xdkj.campus.menu.fragment.ShopFragment;
import com.xdkj.campus.menu.view.BottomBar;
import com.xdkj.campus.menu.view.BottomBarTab;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by arilpan@qq.com on 2016/8/4.
 */
public class MainFragment extends BaseFragment {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_activity_main, container, false);
        if (savedInstanceState == null) {
            mFragments[FIRST] = IndexFragment.newInstance();
            mFragments[SECOND] = DiscountFragment.newInstance();
            mFragments[THIRD] = ZhihuThirdFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
            Log.e("arilpan", "MainFragment savedInstanceState == null ");
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(IndexFragment.class);
            mFragments[SECOND] = findFragment(DiscountFragment.class);
            mFragments[THIRD] = findFragment(ZhihuThirdFragment.class);
            mFragments[FOURTH] = findFragment(MineFragment.class);
            Log.e("arilpan", "MainFragment savedInstanceState != null ");
        }

        initView(view);
        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
//        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    private void initView(View view) {
        EventBus.getDefault().register(this);
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(_mActivity, R.drawable.ic_home_white_24dp, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, "优惠"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp, "留言-点餐"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, int prePosition) {
                Log.e("arilpan", "MainFragment onTabSelected position: " + position
                        + ",prePosition:" + prePosition);
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
//                EventBus.getDefault().post(new TabSelectedEvent(position));
//                Log.e("arilpan", "MainFragment OnTabSelectedListener position: " + position);
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
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    private static final int REQ_MSG = 10;

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
