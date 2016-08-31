package com.xdkj.campus.menu.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.RechargeDiscountAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.message.APPRechargeDiscount;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Recharge;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DiscountFragment extends BaseFragment
{
    public DiscountFragment()
    {
    }

    public static DiscountFragment newInstance(String shop_id)
    {
        Bundle args = new Bundle();
        args.putString("shop_id", shop_id);
        DiscountFragment fragment = new DiscountFragment();
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
            shop_id = args.getString("shop_id");
        }
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

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    RecyclerView listview;
    TextView mTvBtnSettings;
    SimpleAdapter simpleAdapter;
    String shop_id;
    RechargeDiscountAdapter rda;

    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        ((TextView) view.findViewById(R.id.title_middle)).setText("优惠");
        listview = (RecyclerView) view.findViewById(R.id.listview);

        shop_id = "ba262eba-05da-4886-947c-5a557c954af5";
        Log.e("arilpan","优惠:"+shop_id);


//        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
//
//        Recharge recharge = new Recharge(1,
//                R.drawable.chongzhi_dianpu,
//                "8号餐馆", "丽人卡", "充2000元送500元",
//                "2016-07-09--2016-08-09");
//        recharge.setBackgoundRes(R.drawable.chongzhi_lirenkabj);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("item_bg", recharge.getBackgoundRes());
//        map.put("item_image", recharge.getMallRes());
//        map.put("item_mall_name", recharge.getMall_name());
//        map.put("item_card_name", recharge.getCard_name());
//        map.put("item_desc", recharge.getDesc());
//        map.put("item_time", recharge.getTime());
//        item.add(map);
//        recharge = new Recharge(1,
//                R.drawable.chongzhi_dianpu,
//                "8号餐馆", "黄金卡", "充1000元送200元",
//                "2016-07-09--2016-08-09");
//        recharge.setBackgoundRes(R.drawable.chongzhi_jinkabj);
//        map = new HashMap<String, Object>();
//        map.put("item_bg", recharge.getBackgoundRes());
//        map.put("item_image", recharge.getMallRes());
//        map.put("item_mall_name", recharge.getMall_name());
//        map.put("item_card_name", recharge.getCard_name());
//        map.put("item_desc", recharge.getDesc());
//        map.put("item_time", recharge.getTime());
//        item.add(map);
//        recharge = new Recharge(1,
//                R.drawable.chongzhi_dianpu,
//                "8号餐馆", "银卡", "充500元送50元",
//                "2016-07-09--2016-08-09");
//        recharge.setBackgoundRes(R.drawable.chongzhi_yinkabj);
//        map = new HashMap<String, Object>();
//        map.put("item_bg", recharge.getBackgoundRes());
//        map.put("item_image", recharge.getMallRes());
//        map.put("item_mall_name", recharge.getMall_name());
//        map.put("item_card_name", recharge.getCard_name());
//        map.put("item_desc", recharge.getDesc());
//        map.put("item_time", recharge.getTime());
//        item.add(map);
//
//        simpleAdapter = new SimpleAdapter(
//                getContext(), item,
//                R.layout.fragment_discount_list_item, new String[]
//                {
//                        "item_bg",
//                        "item_image",
//                        "item_mall_name",
//                        "item_card_name",
//                        "item_desc",
//                        "item_time"
//                },
//                new int[]{
//                        R.id.item_bg,
//                        R.id.item_image,
//                        R.id.item_mall_name,
//                        R.id.item_card_name,
//                        R.id.item_desc,
//                        R.id.item_time}
//        );
//
//        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder()
//        {
//            public boolean setViewValue(View view, Object data,
//                                        String textRepresentation)
//            {
//                //判断是否为我们要处理的对象
//                if (view instanceof ImageView && data instanceof Integer)
//                {
//                    ImageView iv = (ImageView) view;
//                    iv.setImageResource(Integer.parseInt(data.toString()));
//                    return true;
//                } else if (view instanceof ImageView && data instanceof Bitmap)
//                {
//                    ImageView iv = (ImageView) view;
//                    iv.setImageBitmap((Bitmap) data);
//                    return true;
//                } else if (view instanceof LinearLayout && data instanceof Integer)
//                {
//                    LinearLayout ll = (LinearLayout) view;
//                    ll.setBackgroundResource(Integer.parseInt(data.toString()));
//                    return true;
//                } else
//                {
//                    return false;
//                }
//            }
//        });
//        listview.setAdapter(simpleAdapter);
        listview.setHasFixedSize(true);
        listview.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager
                        .VERTICAL));
        rda = new RechargeDiscountAdapter(_mActivity);
        listview.setAdapter(rda);

        EventBus.getDefault().post(new NetworkEvent(
                RequestType.DISCOUNT_CARD_LIST,
                shop_id));
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan","优惠: receive event");
        if (RequestType.DISCOUNT_CARD_LIST == event.reqType)
        {
            Log.e("arilpan", "discount card list equals url="
                    + event.url + event.id);
            setData(getData(event.url + event.id));
        }
        Log.e("arilpan", "what happend in the card");
    }

    public List<APPRechargeDiscount.ValueBean> getData(String url)
    {
        try
        {
            final JsonAdapter<APPRechargeDiscount>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPRechargeDiscount.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPRechargeDiscount datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            datas = datas_arry.getValue();
            for (APPRechargeDiscount.ValueBean data : datas)
            {
                Log.e("arilpan", data.getShop_name() +
                        ",code :" + data.getCard_name());
            }
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    List<APPRechargeDiscount.ValueBean> datas;

    public void setData(final List<APPRechargeDiscount.ValueBean> items)
    {
        try
        {

            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (items == null)
                    {
                        Toast.makeText(getContext(), "请刷新列表", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    rda.setDatas(items);

                    //stuff that updates ui
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
