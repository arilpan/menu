package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.fragment.ShopFragment;
import com.xdkj.campus.menu.base.BaseFragment;


/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class CommentFragment extends BaseFragment
{
    TextView comment;
    Button comment_button;

    public CommentFragment()
    {
        // Required empty public constructor
    }

    public static CommentFragment newInstance()
    {

        Bundle args = new Bundle();
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        initView(view);
        return view;
    }


    private void initView(View view)
    {
        comment = (TextView) view.findViewById(R.id.comment);
        comment_button = (Button) view.findViewById(R.id.comment_button);

        comment_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //提交后退出
                start(ShopFragment.newInstance());
            }
        });
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
}
