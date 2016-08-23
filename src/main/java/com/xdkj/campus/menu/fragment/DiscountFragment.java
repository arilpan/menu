package com.xdkj.campus.menu.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Recharge;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DiscountFragment extends BaseFragment
{
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
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        initView(view);
        return view;
    }

    ListView listview;
    TextView mTvBtnSettings;

    private void initView(View view)
    {
        ((TextView) view.findViewById(R.id.title_middle)).setText("优惠");

        listview = (ListView) view.findViewById(R.id.listview);
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();

        Recharge recharge = new Recharge(1,
                R.drawable.chongzhi_dianpu,
                "8号餐馆", "丽人卡", "充2000元送500元",
                "2016-07-09--2016-08-09");
        recharge.setBackgoundRes(R.drawable.chongzhi_lirenkabj);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item_bg", recharge.getBackgoundRes());
        map.put("item_image", recharge.getMallRes());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);
        recharge = new Recharge(1,
                R.drawable.chongzhi_dianpu,
                "8号餐馆", "黄金卡", "充1000元送200元",
                "2016-07-09--2016-08-09");
        recharge.setBackgoundRes(R.drawable.chongzhi_jinkabj);
        map = new HashMap<String, Object>();
        map.put("item_bg", recharge.getBackgoundRes());
        map.put("item_image", recharge.getMallRes());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);
        recharge = new Recharge(1,
                R.drawable.chongzhi_dianpu,
                "8号餐馆", "银卡", "充500元送50元",
                "2016-07-09--2016-08-09");
        recharge.setBackgoundRes(R.drawable.chongzhi_yinkabj);
        map = new HashMap<String, Object>();
        map.put("item_bg", recharge.getBackgoundRes());
        map.put("item_image", recharge.getMallRes());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), item,
                R.layout.fragment_discount_list_item, new String[]
                {
                        "item_bg",
                        "item_image",
                        "item_mall_name",
                        "item_card_name",
                        "item_desc",
                        "item_time"
                },
                new int[]{
                        R.id.item_bg,
                        R.id.item_image,
                        R.id.item_mall_name,
                        R.id.item_card_name,
                        R.id.item_desc,
                        R.id.item_time}
        );
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder()
        {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation)
            {
                //判断是否为我们要处理的对象
                if (view instanceof ImageView && data instanceof Integer)
                {
                    ImageView iv = (ImageView) view;
                    iv.setImageResource(Integer.parseInt(data.toString()));
                    return true;
                } else if (view instanceof ImageView && data instanceof Bitmap)
                {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                } else if (view instanceof LinearLayout && data instanceof Integer)
                {
                    LinearLayout ll = (LinearLayout) view;
                    ll.setBackgroundResource(Integer.parseInt(data.toString()));
                    return true;
                } else
                {
                    return false;
                }
            }
        });
        listview.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
