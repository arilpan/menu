package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SelectPlaceFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


//    private OnFragmentInteractionListener mListener;

    public SelectPlaceFragment() {
        // Required empty public constructor
    }

    public static SelectPlaceFragment newInstance() {

        Bundle args = new Bundle();
        SelectPlaceFragment fragment = new SelectPlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_mall, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }
}
