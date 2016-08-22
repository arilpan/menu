package com.xdkj.campus.menu.adapter.child;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class HotDishPagerAdapter extends RecyclerView.Adapter<HotDishPagerAdapter.MyViewHolder>
{
    private List<Dish> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public HotDishPagerAdapter(Context context)
    {
        this.mInflater = LayoutInflater.from(context);

    }

    public void setDatas(List<Dish> items)
    {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = mInflater.inflate(R.layout.index_list_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getAdapterPosition();
                if (mClickListener != null)
                {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if (mItems != null)
        {
            Dish item = mItems.get(position);
            Log.e("arilpan","position:" + position +", tostring:"+item.toString());
            holder.name.setText(item.getName());
            holder.desc.setText(item.getDesc());
            holder.price.setText(item.getPrice());

            holder.sold_num.setText("已售:"+item.getNum());
            holder.mall_price.setText(item.getMallprice());
            //holder.image.setImageResource
            // (R.drawable.index_dishes_image_default);

        }
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView price;
        private TextView desc;
        private TextView name;

        private TextView mall_price;
        private TextView sold_num;
        private ImageView image;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.dish_name);
            desc = (TextView) itemView.findViewById(R.id.dish_desc);
            price = (TextView) itemView.findViewById(R.id.dish_price);
            mall_price = (TextView) itemView.findViewById(R.id.dish_mall_price);
            sold_num = (TextView) itemView.findViewById(R.id.dish_sold_num);
            image = (ImageView) itemView.findViewById(R.id.dish_image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }
}