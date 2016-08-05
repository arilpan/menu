package com.xdkj.campus.menu.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.ShopFragment;
import com.xdkj.campus.menu.adapter.MenuAdapter;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import java.util.ArrayList;

/**
 * Created by YoKeyword on 16/2/9.
 */
public class MenuListFragment extends BaseFragment {
    private static final String ARG_MENUS = "arg_menus";
    private static final String ARG_ADDMENUS = "arg_addmenus";
    private static boolean SHOW = false;

    private static final String SAVE_STATE_POSITION = "save_state_position";

    private RecyclerView mRecy;
    private MenuAdapter mAdapter;

    private ArrayList<String> mMenus;

    private ArrayList<String> mAddMenus;
    private int mCurrentPosition = -1;

    public static MenuListFragment newInstance(ArrayList<String> menus, ArrayList<String> addmenus) {

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_MENUS, menus);
        args.putStringArrayList(ARG_ADDMENUS, addmenus);


        MenuListFragment fragment = new MenuListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mMenus = args.getStringArrayList(ARG_MENUS);
            mAddMenus = args.getStringArrayList(ARG_ADDMENUS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_menu, container, false);
        initView(view);
        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mAdapter = new MenuAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);
        mAdapter.setDatas(mMenus);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                showContent(position);
            }
        });

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SAVE_STATE_POSITION);
            mAdapter.setItemChecked(mCurrentPosition);
        } else {
            mCurrentPosition = 0;
            mAdapter.setItemChecked(0);
        }
    }

    private void showContent(int position) {
        if (position == mCurrentPosition) {
            return;
        }
        if (position == 3) {
            if (SHOW) {
                for (String s : mAddMenus) {
                    mMenus.remove(s);
                }
                LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                mRecy.setLayoutManager(manager);
                mAdapter = new MenuAdapter(_mActivity);
                mRecy.setAdapter(mAdapter);
                mAdapter.setDatas(mMenus);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                        showContent(position);
                    }
                });
                SHOW = false;
            } else {
                for (String s : mAddMenus) {
                    mMenus.add(s);
                }
                LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                mRecy.setLayoutManager(manager);
                mAdapter = new MenuAdapter(_mActivity);
                mRecy.setAdapter(mAdapter);
                mAdapter.setDatas(mMenus);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                        showContent(position);
                    }
                });
                SHOW = true;
            }

            return;
        }

        mCurrentPosition = position;

        mAdapter.setItemChecked(position);

//        ContentFragment fragment = ContentFragment.newInstance(mMenus.get(position));
//        ((ShopFragment) getParentFragment()).switchContentFragment(fragment);


        SelectFragment fragment1 = SelectFragment.newInstance(position < 3 ? mMenus.get(position) :
                mAddMenus.get(position-4), "");
        ((ShopFragment) getParentFragment()).switchSelectFragment(fragment1);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_POSITION, mCurrentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
    }
}
