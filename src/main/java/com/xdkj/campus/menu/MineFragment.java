package com.xdkj.campus.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.ui.order.CancleOrderFragment;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TestOne.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TestOne#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private OnFragmentInteractionListener mListener;
    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    RelativeLayout running_layout;
    RelativeLayout complete_layout;
    RelativeLayout uncomplete_layout;

//    TextView mTvBtnSettings;

    private void initView(View view) {
//        mTvBtnSettings = (TextView) view.findViewById(R.id.tv);
//        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                start(MineFragment.newInstance());
//            }
//        });
        running_layout = (RelativeLayout) view.findViewById(R.id.running_layout);
        complete_layout = (RelativeLayout) view.findViewById(R.id.complete_layout);
        uncomplete_layout = (RelativeLayout) view.findViewById(R.id.uncomplete_layout);
        running_layout.setOnClickListener(this);
        complete_layout.setOnClickListener(this);
        complete_layout.setOnClickListener(this);

        ((TextView)view.findViewById(R.id.title_middle)).setText("我的");
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.running_layout:
                start(CancleOrderFragment.newInstance());
                break;
            case R.id.complete_layout:
                start(MineFragment.newInstance());
                break;
            case R.id.uncomplete_layout:
                start(MineFragment.newInstance());
                break;
            case R.id.setting_layout:
                start(MineFragment.newInstance());
                break;
        }

    }
}
