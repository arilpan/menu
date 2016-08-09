package com.xdkj.campus.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Recharge;

import java.util.ArrayList;
import java.util.HashMap;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TestOne.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TestOne#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DiscountFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


//    private OnFragmentInteractionListener mListener;

    public DiscountFragment() {
        // Required empty public constructor
    }

    public static DiscountFragment newInstance() {

        Bundle args = new Bundle();
        DiscountFragment fragment = new DiscountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        initView(view);
        return view;
    }

    ListView listview;
    TextView mTvBtnSettings;

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.title_middle)).setText("优惠");

        listview = (ListView) view.findViewById(R.id.listview);
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();

//        for (int i = 0; i < 3; i++) {
        Recharge recharge = new Recharge(1,
                R.drawable.chongzhi_lirenkabj,
                "8号餐馆", "丽人卡", "充2000元送500元",
                "2016-07-09--2016-08-09");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item_image", recharge.getImage());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);
        recharge = new Recharge(1,
                R.drawable.chongzhi_jinkabj,
                "8号餐馆", "黄金卡", "充1000元送200元",
                "2016-07-09--2016-08-09");
        map = new HashMap<String, Object>();
        map.put("item_image", recharge.getImage());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);
        recharge = new Recharge(1,
               R.drawable.chongzhi_yinkabj,
                "8号餐馆", "银卡", "充500元送50元",
                "2016-07-09--2016-08-09");
        map = new HashMap<String, Object>();
        map.put("item_image", recharge.getImage());
        map.put("item_mall_name", recharge.getMall_name());
        map.put("item_card_name", recharge.getCard_name());
        map.put("item_desc", recharge.getDesc());
        map.put("item_time", recharge.getTime());
        item.add(map);
//        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), item,
                R.layout.fragment_discount_list_item, new String[]
                {"item_image", "item_mall_name", "item_card_name",
                        "item_desc", "item_time"},
                new int[]{R.id.item_image, R.id.item_mall_name, R.id.item_card_name,
                        R.id.item_desc, R.id.item_time}) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return super.getView(position, convertView, parent);
//            }
        };
        listview.setAdapter(simpleAdapter);
//        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                start(DiscountFragment.newInstance());
//            }
//        });
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
