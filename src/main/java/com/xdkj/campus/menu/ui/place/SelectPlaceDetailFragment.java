package com.xdkj.campus.menu.ui.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.bigkoo.pickerview.TimePickerView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.APIAddrFactory;
import com.xdkj.campus.menu.api.message.APPRoomInfo;
import com.xdkj.campus.menu.api.message.APPSelectRight;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.helper.GetParamConstructor;
import com.xdkj.campus.menu.ui.menu.DishList;
import com.xdkj.campus.menu.ui.menu.MenuListFragment;
import com.xdkj.campus.menu.ui.menu.SelectFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Create/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class SelectPlaceDetailFragment extends SupportFragment
        implements IListDialogListener, ISimpleDialogCancelListener
{
    private static final String ARG_NUMBER = "shop_id";
    private String shop_id;

    public static SelectPlaceDetailFragment newInstance(String shop_id)
    {
        SelectPlaceDetailFragment fragment = new SelectPlaceDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUMBER, shop_id);
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
            shop_id = args.getString(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);
        initView(view);
        return view;
    }

    //pre_order_list_small_rl
    // 用餐人数
    // 用餐时间
    // 包间
    // 选择菜品
    RelativeLayout orderPersonNumRl;
    RelativeLayout orderTimeRl;
    RelativeLayout orderRoomRl;
    RelativeLayout orderSelectDishRl;
    TextView select_room;
    TextView select_time;
    TextView select_person_num;
    Button order;

    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new NetworkEvent(RequestType.INDEX_DISH_SELECT_ROOM, shop_id));

        view.findViewById(R.id.title_ll_left).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _mActivity.onBackPressed();
            }
        });

        orderPersonNumRl = (RelativeLayout) view.findViewById(R.id.order_person_num);
        orderTimeRl = (RelativeLayout) view.findViewById(R.id.order_time);
        orderRoomRl = (RelativeLayout) view.findViewById(R.id.order_room);
        orderSelectDishRl = (RelativeLayout) view.findViewById(R.id.order_dishes);

        select_room = (TextView) view.findViewById(R.id.select_room);
        select_person_num = (TextView) view.findViewById(R.id.select_num);
        select_time = (TextView) view.findViewById(R.id.select_time);

        order = (Button) view.findViewById(R.id.order);
        /**
         * check
         * <p>
         *     <li>shop_id</li>
         *     <li>dish num>1 dish </li>
         *     <li>time>2 hours</li>
         *     <li>person number\room number select </li>
         * </p>
         */
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * 手机端传来相应的用户唯一标识user_id(手机端用户注册时的手机号信息)，这个用户唯一标识user_id和手机端添加的
                 订单信息（用餐人数have_meals_persons、用餐时间have_meals_time、备注remarks、shop_id订单所属店铺主键，
                 shop_name店铺名称、room包间名称、sum_price所选菜品总价格），shop_id、shop_name是用户进入相应店铺时，web后台
                 返回给手机端的departManag这个对象中，从这个对象中取出shop_id、shop_name连同添加的用餐人数等信息封装在str这个对象中，
                 然后将str返回给web后台。
                 用户选择的菜品信息列表（菜品的名称dishes_name、菜品的价格dishes_price、菜品的数量dishes_count）封装在jsonStr这个对象中，
                 然后将jsonStr返回给web后台
                 手机端访问:
                 http://172.16.0.56:8080/GrogshopSystem/appOrder/saveAddOrder.do
                 */
                String user_id = APIAddr.user_id;
                String have_meals_persons = select_person_num.getText().toString();
                String have_meals_time = select_person_num.getText().toString();
                String remarks = select_person_num.getText().toString();
                //shop_id;
                String shop_name = ((shop_id == APIAddr.shop_one_id) ? APIAddr.shop_one_name :
                        APIAddr.shop_two_name);
                String room = select_room.getText().toString();
                double sum_price = DishList.getTotalPrice();
                //1. str :上面8个参数toJson=>后来告诉我其实是普通get方式
                // ,7000字符容量还够用= =!!!多亏了谷歌内核

                GetParamConstructor getParamConstructor = new GetParamConstructor();
                getParamConstructor.add("user_id", user_id);
                getParamConstructor.add("have_meals_persons", have_meals_persons);
                getParamConstructor.add("have_meals_time", have_meals_time);
                getParamConstructor.add("remarks", remarks);
                getParamConstructor.add("shop_id", shop_id);
                getParamConstructor.add("shop_name", shop_name);
                getParamConstructor.add("room", room);
                getParamConstructor.add("sum_price", String.valueOf(sum_price));

                JSONObject jsonObj = new JSONObject();
                try
                {
                    jsonObj.put("str", getParamConstructor);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Log.e("arilpan", "-------------" + jsonObj.toString());

                //2.jsonStr
                List<TempDish> dishes = new ArrayList<>();
                for (Dish dish : DishList.getlist())
                {
                    String dishes_name = dish.getName();
                    String dishes_price = dish.getPrice();
                    int dishes_count = dish.getNum();

                    dishes.add(new TempDish(dishes_name, dishes_price, dishes_count));

                }
                jsonStr tds = new jsonStr(dishes);
//                TempDish blackjackHand = new TempDish(
//                        new TempDish('6', SPADES),
//                        Arrays.asList(new Dish('4', CLUBS), new Dish('A', HEARTS)));
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<jsonStr> jsonAdapter = moshi.adapter(jsonStr.class);
                String json = jsonAdapter.toJson(tds);
                Log.e("arilpan", "-------------" + json.toString());
                getParamConstructor.add("jsonStr", json.toString());
                try
                {
                    jsonObj.put("jsonStr", dishes);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Log.e("arilpan", "after:-------------" + jsonObj.toString());

            }
        });


        orderSelectDishRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择菜品
            }
        });
        orderRoomRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择房间
                ListDialogFragment
                        .createBuilder(getContext(), getFragmentManager())
                        .setTitle("选择包间:")
                        .setItems(new String[]{})
                        .setTargetFragment(SelectPlaceDetailFragment.this, SELECT_ROOM)
                        .show();
            }
        });
        orderPersonNumRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用填写人数
                //调用选择房间
                ListDialogFragment
                        .createBuilder(getContext(), getFragmentManager())
                        .setTitle("选择用餐人数:")
                        .setItems(new String[]{"1", "2", "3",
                                "4", "5", "6", "7", "8", "9",
                                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"
                        })
                        .setTargetFragment(SelectPlaceDetailFragment.this, SELECT_PERSON_NUM)
                        .show();
            }
        });
        vMasker = (View) view.findViewById(R.id.vMasker);
        initTimeSelect();
        orderTimeRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //弹出时间选择器
                pvTime.show();
            }
        });

    }

    static class jsonStr
    {
        public final List<TempDish> dishes;

        jsonStr()
        {
            dishes = null;
        }

        jsonStr(List<TempDish> dishes)
        {
            this.dishes = dishes;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //time start

    TimePickerView pvTime;
    View vMasker;

    public void initTimeSelect()
    {
        //调用选择时间
        //时间选择器
        pvTime = new TimePickerView(getContext(), TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.YEAR) + 1);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(Date date)
            {
//                        tvTime.setText(getTime(date));
                Log.e("arilpan", getTime(date));
                select_time.setText(getTime(date));
            }
        });
    }

    public static String getTime(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        if (pvTime.isShowing())
        {
            pvTime.dismiss();
            return true;
        }
        return super.onBackPressedSupport();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//
//            if (pvTime.isShowing())
//            {
//                pvTime.dismiss();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //time end

    private static final int SELECT_PERSON_NUM = 11;
    private static final int SELECT_TIME = 10;
    private static final int SELECT_ROOM = 9;

    @Override
    public void onListItemSelected(CharSequence value, int number, int requestCode)
    {
        if (requestCode == SELECT_ROOM)
        {
            select_room.setText(value + "-" + String.valueOf(number));
        }
        if (requestCode == SELECT_PERSON_NUM)
        {
            select_person_num.setText(value);
        }
    }

    @Override
    public void onCancelled(int requestCode)
    {
        switch (requestCode)
        {
            case SELECT_TIME:
                Toast.makeText(getContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                break;
            case SELECT_ROOM:
            case SELECT_PERSON_NUM:
                Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
                break;
//            case REQUEST_DATE_PICKER:
//                Toast.makeText(c, "Date picker cancelled", Toast.LENGTH_SHORT).show();
//                break;
//            case REQUEST_TIME_PICKER:
//                Toast.makeText(c, "Time picker cancelled", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {

        if (RequestType.INDEX_DISH_SELECT_ROOM == event.reqType)
        {
            Log.e("arilpan", "ShopFragment 填充数据");
            setData(getData(event));

        }

    }

    public void setData(final List<APPRoomInfo.ValueBean> datas)
    {
        try
        {
            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
//                    mAdapter.setDatas(items);
                    //stuff that updates ui

                    orderRoomRl.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (datas == null)
                            {
                                Toast.makeText(getContext(), "还未获取到包间数据", Toast.LENGTH_SHORT)
                                        .show();
                                return;
                            }
                            //调用选择房间
                            String[] rooms = new String[datas.size()];
                            int id = 0;
                            for (APPRoomInfo.ValueBean vb : datas)
                            {
                                vb.getRoom_id();
                                rooms[id++] = vb.getRoom_title();
                            }
                            ListDialogFragment
                                    .createBuilder(getContext(), getFragmentManager())
                                    .setTitle("选择包间:")
                                    .setItems(rooms)
                                    .setTargetFragment(SelectPlaceDetailFragment.this, SELECT_ROOM)
                                    .show();
                        }
                    });


                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public List<APPRoomInfo.ValueBean> getData(NetworkEvent event)
    {
        try
        {
            final JsonAdapter<APPRoomInfo>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPRoomInfo.class));
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(event.url + event.id)
                    .build();

            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPRoomInfo datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            Log.e("arilpan", "room num:" + datas_arry.getValue().size());
            return datas_arry.getValue();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
