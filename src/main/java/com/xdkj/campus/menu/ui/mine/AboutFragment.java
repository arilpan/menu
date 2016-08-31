package com.xdkj.campus.menu.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseBackFragment;

/**
 * Created by aril_pan@qq.com on 16-8-31.
 */
public class AboutFragment extends BaseBackFragment
{
    static String ARG_NUMBER = "arg_number";
    int mNumber;

    public static AboutFragment newInstance(int number)
    {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
    }
}
