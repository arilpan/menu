package com.xdkj.campus.menu.backup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TestOne.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TestOne#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TestOne extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


//    private OnFragmentInteractionListener mListener;

    public TestOne() {
        // Required empty public constructor
    }

    public static TestOne newInstance() {

        Bundle args = new Bundle();
        TestOne fragment = new TestOne();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_one, container, false);
        initView(view);
        return view;
    }

    TextView mTvBtnSettings;

    private void initView(View view) {
        mTvBtnSettings = (TextView) view.findViewById(R.id.tv);
        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(TestOne.newInstance());
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan","on back press");
        return false;
//        return true;
    }
}
