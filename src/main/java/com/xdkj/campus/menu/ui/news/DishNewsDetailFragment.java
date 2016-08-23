package com.xdkj.campus.menu.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.News;


/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishNewsDetailFragment extends BaseFragment
{
    public static int dish_id;

    static News dishnew;
    private TextView title;
    private TextView time;
    private TextView content;

    public DishNewsDetailFragment()
    {
    }

    public static DishNewsDetailFragment newInstance(News news)
    {
        dishnew = news;
        Bundle args = new Bundle();
        DishNewsDetailFragment fragment = new DishNewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_news_detail, container, false);
        initView(view);
        return view;
    }


    private void initView(View view)
    {
        title = (TextView) view.findViewById(R.id.title);
        time = (TextView) view.findViewById(R.id.time);
        content = (TextView) view.findViewById(R.id.content);
        title.setText(dishnew.getTitle());
        time.setText(dishnew.getTime());
        content.setText(dishnew.getContent());
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
