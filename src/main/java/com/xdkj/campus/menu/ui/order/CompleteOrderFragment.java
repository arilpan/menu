package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.PagerAdapter;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.Order;
import com.xdkj.campus.menu.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/***
 * 已完成订单
 * Created by aril_pan@qq.com on 16/8.
 */
public class CompleteOrderFragment extends BaseFragment
{

    public CompleteOrderFragment()
    {
    }

    public static CompleteOrderFragment newInstance()
    {
        Bundle args = new Bundle();
        CompleteOrderFragment fragment = new CompleteOrderFragment();
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
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$11");
                    mData.add(dish);
                    order.setTotalPrice("20$");
                    order.setPre_order_time("2017-06-04");
                    break;
                case 2:
                    order.setPre_order_time("2017-06-11");
                    order.setTotalPrice("40$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$22");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$22");
                    mData.add(dish);
                    break;
                case 3:
                    order.setPre_order_time("2017-07-03");
                    order.setTotalPrice("60$");

                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$33");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$33");
                    mData.add(dish);
                    break;
                case 4:
                    order.setPre_order_time("2017-08-12");
                    order.setTotalPrice("80$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$44");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$44");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$44");
                    mData.add(dish);
                    break;
                case 5:
                    order.setTotalPrice("100$");
                    order.setPre_order_time("2017-08-15");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$55");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$55");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$55");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$55");
                    mData.add(dish);
                    break;
                case 6:
                    order.setTotalPrice("120$");
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$66");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$66");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$66");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$66");
                    mData.add(dish);
                    dish = new Dish("酸菜鱼", "开始大家案例疯狂啦的你们呢就服务空气节目额", "$66");
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
        Log.e("arilpan", "on back press");
        return false;
    }


    /****************************************************************************/
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        List<Dish> itemDishes;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(
                    R.layout.fragment_order_cancel_order_item,
                    parent,
                    false));
/****************************************************************************/
            ViewGroup.LayoutParams layoutParams =
                    holder.order_item_recyview.getLayoutParams();
            layoutParams.height = 35;
            if (viewType > 0)
            {
                layoutParams.height = 35 + viewType * 230;
                Log.e("arilpan", " dishes size: " + viewType + ",height:" +
                        layoutParams.height);
            }
            holder.order_item_recyview.setLayoutManager(new LinearLayoutManager
                    (_mActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, true));
            holder.order_item_recyview.setLayoutParams(layoutParams);
/****************************************************************************/
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.order_item_recyview.setMinimumHeight(35);
            Order order = mDatas.get(position);
            holder.order_item_mall_name.setText(order.getShopName());
            holder.order_item_order_time.setText(order.getPre_order_time());
            holder.order_item_total_price.setText(order.getTotalPrice());

            holder.order_item_recyview.setAdapter(
                    new RecyclerView.Adapter()
                    {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(
                                ViewGroup parent, int
                                viewType)
                        {
                            MyItemViewHolder itemholder = new MyItemViewHolder(
                                    LayoutInflater.from(parent.getContext()).
                                            inflate(R.layout.fragment_order_complete_list_item,
                                                    parent,
                                                    false));
                            return itemholder;
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                                     int position)
                        {
                            MyItemViewHolder newholder = (MyItemViewHolder) holder;

                            String name = null;
                            Log.e("arilpan", "该item data的大小" + itemDishes.size());
                            if (position < itemDishes.size())
                            {
                                Dish dish = itemDishes.get(position);
                                name = dish.getName();
                                newholder.dish_name.setText(dish.getName());
                                newholder.dish_price.setText(dish.getPrice());
                                newholder.dish_desc.setText(dish.getDesc());
                            } else
                            {
                                Log.e("arilpan",
                                        " will throw java.lang.IndexOutOfBoundsException");
                            }
                            Log.e("arilpan", "item onBindViewHolder position: "
                                    + position + " ," +
                                    "size : " + itemDishes.size());
                            final int pos = position;
                            final String dish_name = name;
                            newholder.dish_comment.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    goToComment(dish_name, pos);
                                }
                            });
                        }

                        public void goToComment(String name, int pos)
                        {
                            //跳轉到評價頁面
                            Log.e("arilpan", "電價了評價位置 name :" + name + ",pos :" + pos);
                            EventBus.getDefault().post(
                                    new StartBrotherEvent(CommentFragment.newInstance()));
                        }

                        @Override
                        public int getItemViewType(int position)
                        {
                            return 100;
                        }

                        @Override
                        public int getItemCount()
                        {
                            return itemDishes.size();
                        }

                        class MyItemViewHolder extends RecyclerView.ViewHolder
                        {
                            ImageView dish_icon;
                            TextView dish_name;
                            TextView dish_price;
                            TextView dish_desc;
                            Button dish_comment;

                            public MyItemViewHolder(View view)
                            {
                                super(view);
                                view.setMinimumHeight(35);

                                dish_icon = (ImageView) view.findViewById(R.id
                                        .dish_icon);
                                dish_name = (TextView) view.findViewById(R.id
                                        .dish_name);
                                dish_price = (TextView) view.findViewById(R.id
                                        .dish_price);
                                dish_desc = (TextView) view.findViewById(R.id
                                        .dish_desc);

                                dish_comment = (Button) view.findViewById(R.id
                                        .dish_comment);
                            }
                        }
                    }
            );
        }

        @Override
        public int getItemViewType(int position)
        {
            Order order = mDatas.get(position);
            itemDishes = order.getDishes();
            if (itemDishes != null)
            {
                return itemDishes.size();
            }
            return 0;
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
