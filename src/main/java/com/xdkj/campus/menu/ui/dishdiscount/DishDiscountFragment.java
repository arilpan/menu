package com.xdkj.campus.menu.ui.dishdiscount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.Order;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/***
 * 折扣菜
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishDiscountFragment extends BaseFragment
{

    public DishDiscountFragment()
    {
    }

    public static DishDiscountFragment newInstance()
    {
        Bundle args = new Bundle();
        DishDiscountFragment fragment = new DishDiscountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_discount, container, false);
        initView(view);
        return view;
    }

    RecyclerView order_recyview;
    private HomeAdapter mAdapter;
    private List<Order> mDatas;

    private void initView(View view)
    {

        order_recyview = (RecyclerView)
                view.findViewById(R.id.dish_discount_recyview);
        order_recyview.setLayoutManager(
                new LinearLayoutManager(view.getContext(),
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
            order.setShopName("8号餐馆");
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
        List<Dish> itemDishes;
        private OnItemClickListener mClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_dish_discount_list_item, parent,
                    false));
/**************************設置高度信息****1  2  2* =>都是5个单位，为何*************************************/

            ViewGroup.LayoutParams layoutParams =
                    holder.dish_discount_item_recyview.getLayoutParams();
            layoutParams.height = 35;
            if (viewType > 0)
            {
                layoutParams.height = 35 + viewType * 250;
                int addTotal = 0;
                int add = 1;
                int numItem = viewType;
                while (add != 0)
                {
                    add = numItem / 2;
                    addTotal += add * 10;
                    numItem = add;
                }
                layoutParams.height += addTotal;
                Log.e("arilpan", " dishes size: " + viewType + ",height:" + layoutParams.height);
            }
            holder.dish_discount_item_recyview.setLayoutManager(new LinearLayoutManager
                    (_mActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, true));
            holder.dish_discount_item_recyview.setLayoutParams(layoutParams);
//            holder.order_item_recyview.setLayoutManager(
//                    new LinearLayoutManager(_mActivity.getApplicationContext()));
/****************************************************************************/
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.dish_discount_item_recyview.setMinimumHeight(35);

            Order order = mDatas.get(position);
//            holder.discount_item_type.setText(order.getTotalPrice());
            holder.discount_item_type.setText("50% off");

            holder.dish_discount_item_recyview.setAdapter(
                    new RecyclerView.Adapter()
                    {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int
                                viewType)
                        {
                            final MyItemViewHolder itemholder = new MyItemViewHolder(
                                    LayoutInflater.from(parent.getContext()).
                                            inflate(R.layout.discount_list_item,
                                                    parent,
                                                    false));
                            itemholder.itemView.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    int position = itemholder.getAdapterPosition();
                                    EventBus.getDefault().post(
                                            new StartBrotherEvent(DishDetailFragment.newInstance(1)));
                                }
                            });

                            return itemholder;
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
                        {
                            MyItemViewHolder newholder = (MyItemViewHolder) holder;

                            Log.e("arilpan", "该item data的大小" + itemDishes.size());
                            if (position < itemDishes.size())
                            {
                                Dish dish = itemDishes.get(position);
                                newholder.dish_name.setText(dish.getName());
                                newholder.dish_price.setText(dish.getPrice());
                                newholder.dish_old_price.setText("132");
                                newholder.dish_desc.setText(dish.getDesc());
                            } else
                            {
                                Log.e("arilpan", " will throw java.lang.IndexOutOfBoundsException");
                            }

                            Log.e("arilpan", "item onBindViewHolder position: " + position + " ," +
                                    "size : " + itemDishes.size());
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
                            TextView dish_old_price;
                            TextView dish_desc;

                            public MyItemViewHolder(View view)
                            {
                                super(view);
                                view.setMinimumHeight(35);
                                dish_icon = (ImageView) view.findViewById(R.id
                                        .dish_image);
                                dish_name = (TextView) view.findViewById(R.id
                                        .dish_name);
                                dish_price = (TextView) view.findViewById(R.id
                                        .dish_price);
                                dish_old_price = (TextView) view.findViewById(R.id
                                        .dish_mall_price);
                                dish_desc = (TextView) view.findViewById(R.id
                                        .dish_desc);
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
            RecyclerView dish_discount_item_recyview;
            TextView discount_item_type;

            public MyViewHolder(View view)
            {
                super(view);
                view.setMinimumHeight(35);
                dish_discount_item_recyview = (RecyclerView) view.findViewById(
                        R.id
                                .dish_discount_list_recyview);
                discount_item_type =
                        (TextView) view.findViewById(R.id.discount_item_type);

            }
        }
    }
}
