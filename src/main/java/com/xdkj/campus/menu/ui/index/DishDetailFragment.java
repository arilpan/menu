package com.xdkj.campus.menu.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.message.APPDishDetail;
import com.xdkj.campus.menu.api.message.APPDishDiscount;
import com.xdkj.campus.menu.api.message.APPNew;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.ui.place.SelectPlaceFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 接受菜品id,并且可以进入预约页面
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishDetailFragment extends BaseFragment
{
    public String dish_id;

    public DishDetailFragment()
    {
    }

//    public static DishDetailFragment newInstance(int number)
//    {
//        DishDetailFragment fragment = new DishDetailFragment();
//        Bundle args = new Bundle();
//        args.putString("dish_id", number + "");
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static DishDetailFragment newInstance(String dish_id)
    {
        Bundle args = new Bundle();
        args.putString("dish_id", dish_id);
        DishDetailFragment fragment = new DishDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            dish_id = args.getString("dish_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_detail, container, false);
        initView(view);
        return view;
    }

    public void gotoSubscribe()
    {
        EventBus.getDefault().post(
                new StartBrotherEvent(SelectPlaceFragment.newInstance(dish_id)));
    }

    TextView dish_name;
    TextView dish_price;
    TextView dish_old_price;
    TextView dish_desc;
    TextView dish_pre_order_must_know;
    TextView dish_mall_name;
    TextView dish_mall_addr;
    TextView dish_mall_work_time;

    private void initView(View view)
    {
        EventBus.getDefault().register(this);

        dish_name = (TextView) view.findViewById(R.id.dish_name);
        dish_price = (TextView) view.findViewById(R.id.dish_price);
        dish_old_price = (TextView) view.findViewById(R.id.dish_old_price);
        dish_desc = (TextView) view.findViewById(R.id.dish_desc);
        dish_pre_order_must_know = (TextView) view.findViewById(R.id.dish_pre_order_must_know);
        dish_mall_name = (TextView) view.findViewById(R.id.dish_mall_name);
        dish_mall_addr = (TextView) view.findViewById(R.id.dish_mall_addr);
        dish_mall_work_time = (TextView) view.findViewById(R.id.dish_mall_work_time);

        Button btn_subscribe = (Button) view.findViewById(R.id.btn_subscribe);

        btn_subscribe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gotoSubscribe();
            }
        });
        EventBus.getDefault().post(new NetworkEvent(
                RequestType.INDEX_DISH_DETAIL,
                dish_id));

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "DishDetail 你调用咩?");
        if (RequestType.INDEX_DISH_DETAIL == event.reqType)
        {
            Log.e("arilpan", "DishDetail equals url="
                    + event.url + event.id);
            setData(getData(event.url + event.id));
        } else
        {
            Log.e("arilpan", "DishDetail what happend?");
        }
    }

    public APPDishDetail getData(String url)
    {
        try
        {
//            Type type = Types.newParameterizedType(MockParameterized.class,
//                    Object.class);
//            JsonAdapter<MockParameterized<?>> jsonAdapter =
// moshi.adapter(type);

            final JsonAdapter<APPDishDetail>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPDishDetail.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPDishDetail datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
//            APPDishDetail.ValueBean datas
//                    = datas_arry.getValue();

            return datas_arry;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setData(final APPDishDetail items)
    {
        try
        {
            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (items != null)
                    {
                        APPDishDetail.ValueBean item = items.getValue();
                        APPDishDetail.Value1Bean shop = items.getValue1();
                        dish_name.setText(item.getDishes_name());
                        dish_price.setText("￥" +item.getDishes_price());
                        dish_old_price.setText("￥" +item.getRack_rate());
                        dish_desc.setText(item.getDishes_description());
                        dish_pre_order_must_know.setText(item.getAppointment_notice());
                        dish_mall_name.setText(item.getStore_name());
                        dish_mall_addr.setText(shop.getAddress());
                        dish_mall_work_time.setText(shop.getShop_work_time());
                        //stuff that updates ui
                    }
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
