package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CancleOrderFragment extends BaseFragment
{

    public CancleOrderFragment()
    {
    }

    public static CancleOrderFragment newInstance()
    {
        Bundle args = new Bundle();
        CancleOrderFragment fragment = new CancleOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order_cancel, container, false);
        initView(view);
        return view;
    }

    RecyclerView order_recyview;
    private HomeAdapter mAdapter;
    private List<Order> mDatas;

    private void initView(View view)
    {

        order_recyview = (RecyclerView) view.findViewById(R.id.order_recyview);
        order_recyview.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));

        mDatas = new ArrayList<Order>();
        for (int i = 1; i < 6; i++)
        {
            List mData = new ArrayList<Dish>();
            Order order = new Order();
            Dish dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
            switch (i)
            {
                case 1:
                    order.setTotalPrice("20$");
                    order.setPre_order_time("2017-06-04");
                    break;
                case 2:
                    order.setPre_order_time("2017-06-11");
                    order.setTotalPrice("40$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    break;
                case 3:
                    order.setPre_order_time("2017-07-03");
                    order.setTotalPrice("60$");

                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    break;
                case 4:
                    order.setPre_order_time("2017-08-12");
                    order.setTotalPrice("80$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    break;
                case 5:
                    order.setTotalPrice("100$");
                    order.setPre_order_time("2017-08-15");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    break;
                case 6:
                    order.setTotalPrice("120$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$32");
                    mData.add(dish);
                    break;
                default:
                    break;
            }
            order.setShopName("8号参观");
            order.setDishes(mData);
            mDatas.add(order);
        }
        order_recyview.setAdapter(mAdapter = new HomeAdapter());
//        basicParamInit();
//        initData();
//        initRecyclerView();
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


    /****************************************************************************/
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_order_cancel_order_item, parent,
                    false));


            return holder;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
//          holder.tv.setText(mDatas.get(position));
            holder.order_item_recyview.setMinimumHeight(35);



            Order order = mDatas.get(position);
            holder.order_item_mall_name.setText(order.getShopName());
            holder.order_item_order_time.setText(order.getPre_order_time());
            holder.order_item_total_price.setText(order.getTotalPrice());


//            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams)
//                    holder.order_item_recyview
//                            .getLayoutParams();

//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                    (dm.widthPixels - dip2px(20)) / 3,
//                    (dm.widthPixels - dip2px(20)) / 3);
//            holder.order_item_recyview.setLayoutParams(lp);

/**************************設置高度信息*****************************************/
            final List<Dish> itemDishes = order.getDishes();
            ViewGroup.LayoutParams layoutParams = holder.order_item_recyview.getLayoutParams();
            layoutParams.height = 35;
            if (itemDishes != null)
            {
                int size = itemDishes.size();
                Log.e("arilpan", " dishes size: " + size);
                if (size > 0)
                {
                    layoutParams.height = 35 + size * 100;
                }
            }
            holder.order_item_recyview.setLayoutParams(layoutParams);
/****************************************************************************/


            holder.order_item_recyview.setAdapter(
                    new RecyclerView.Adapter()
                    {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int
                                viewType)
                        {
                            MyItemViewHolder itemholder = new MyItemViewHolder(
                                    LayoutInflater.from(parent.getContext()).
                                            inflate(R.layout.fragment_order_cancel_list_item,
                                                    parent,
                                                    false));


                            return itemholder;
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
                        {
                            MyItemViewHolder newholder = (MyItemViewHolder) holder;
                            List<Dish> data = itemDishes;
                            for (Dish dish : data)
                            {
                                Log.e("arilpan", dish.toString());
                                newholder.dish_name.setText(dish.getName());
                                newholder.dish_price.setText(dish.getPrice());
                                newholder.dish_desc.setText(dish.getDesc());
                            }
                            Log.e("arilpan", "item onBindViewHolder  size : " + data.size());
                        }

                        @Override
                        public int getItemCount()
                        {
                            return mDatas.size();
                        }

                        class MyItemViewHolder extends RecyclerView.ViewHolder
                        {
                            ImageView dish_icon;
                            TextView dish_name;
                            TextView dish_price;
                            TextView dish_desc;

                            public MyItemViewHolder(View view)
                            {
                                super(view);
                                view.setMinimumHeight(35);

                                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                                layoutParams.height = 35;
                                if (itemDishes != null)
                                {
                                    int size = itemDishes.size();
                                    Log.e("arilpan", " dishes size: " + size);
                                    if (size > 0)
                                    {
                                        layoutParams.height = 35 + size * 100;
                                    }
                                }
                                view.setLayoutParams(layoutParams);

                                dish_icon = (ImageView) view.findViewById(R.id
                                        .dish_icon);
                                dish_name = (TextView) view.findViewById(R.id
                                        .dish_name);
                                dish_price = (TextView) view.findViewById(R.id
                                        .dish_price);
                                dish_desc = (TextView) view.findViewById(R.id
                                        .dish_desc);
                            }
                        }
                    }

            );

        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            RecyclerView order_item_recyview;
            TextView order_item_total_price;
            TextView order_item_order_time;
            TextView order_item_mall_name;

            public MyViewHolder(View view)
            {
                super(view);
                view.setMinimumHeight(35);
                order_item_recyview = (RecyclerView) view.findViewById(R.id.order_item_recyview);
                order_item_total_price = (TextView) view.findViewById(R.id.order_item_total_price);
                order_item_order_time = (TextView) view.findViewById(R.id.order_item_order_time);
                order_item_mall_name = (TextView) view.findViewById(R.id.order_item_mall_name);

            }
        }
    }
}
