package com.fan.eightrestaurant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fan.eightrestaurant.adapter.PreferentialAdapter;
import com.fan.eightrestaurant.bean.Preferential;
import com.fan.eightrestaurant.utils.JSONUtils;
import com.fan.eightrestaurant.utils.PathUtils;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 专属优惠信息界面
 */
public class DiscountFragment extends BaseFragment
{
    private ListView listView;
    private List<Preferential> list;
    private PreferentialAdapter adapter;

    public DiscountFragment()
    {
    }

    public static DiscountFragment newInstance()
    {

        Bundle args = new Bundle();

        DiscountFragment fragment = new DiscountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_preferential, container, false);
        initView(view);
        return view;
    }

    public void initView(View view)
    {
        listView = (ListView) view.findViewById(R.id.activity_preferential_listview);
        initData(PathUtils.getPreferentialUrl());
        list = new ArrayList<>();
        //todo:arilpan  new Bundle()
        View listViewHeader = getLayoutInflater(new Bundle()).
                inflate(R.layout.preferential_listview_head,
                        null);
        listView.addHeaderView(listViewHeader);
        adapter = new PreferentialAdapter(getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(),
                        PreferentialDetailsActivity.class);
                String discount_id = list.get((int) id).getDiscount_id();
                String shop_id = list.get((int) id).getShop_id();
                intent.putExtra("discount_id", discount_id);
                intent.putExtra("shop_id", shop_id);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取网络数据
     */
    private void initData(String path)
    {
        OkHttpUtils.get().url(path).build().execute(new StringCallback()
        {
            @Override
            public void onError(Call call, Exception e)
            {
            }

            @Override
            public void onResponse(String response)
            {
                List<Preferential> result = JSONUtils.getPreferentialJson(response);
                list.addAll(result);
            }
        });
    }
}
