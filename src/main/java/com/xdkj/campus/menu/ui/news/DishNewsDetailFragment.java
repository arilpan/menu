package com.xdkj.campus.menu.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.message.APPNewsList;
import com.xdkj.campus.menu.base.BaseFragment;


/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishNewsDetailFragment extends BaseFragment
{
    public static int dish_id;

    APPNewsList.ValueBean.ListBean.DataBean dishnew;
    String news_id;
    private TextView title;
    private TextView time;
    private TextView content;

    public DishNewsDetailFragment()
    {
    }

    public static DishNewsDetailFragment newInstance( String  news_id)
    {

        Bundle args = new Bundle();
        args.putString("news_id", "" + news_id);

        DishNewsDetailFragment fragment = new DishNewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //news_id
        Bundle args = getArguments();
        if (args != null) {
            news_id =  args.getString("news_id");
        }
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
//        title.setText(new.getTitle());
//        time.setText(dishnew.getTime());
//        content.setText(dishnew.getContent());
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
