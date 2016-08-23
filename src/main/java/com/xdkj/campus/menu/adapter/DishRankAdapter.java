package com.xdkj.campus.menu.adapter;

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
 * Created by arilpan@qq.com on 16/8.
 */
public class DishRankAdapter extends RecyclerView.Adapter<DishRankAdapter.MyViewHolder>
{
    private List<Dish> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public DishRankAdapter(Context context)
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

        View view = mInflater.inflate(R.layout.fragment_rank_dishes_list_item, parent, false);
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
        Dish item = mItems.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDesc());
        holder.price.setText(item.getPrice());
        holder.soldNum.setText("  " + item.getNum() + "2688人购买");
//        holder.soldNum.setText(item.getNum() + "人购买");
        //todo : load image
//     holder.image.setImageResource(R.drawable.index_dishes_image_default);
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
        private TextView soldNum;

        private ImageView image;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.fragment_food_list_item_foodname);
            desc = (TextView) itemView.findViewById(R.id.fragment_food_list_item_introduction);
            price = (TextView) itemView.findViewById(R.id.fragment_food_list_item_foodprice);
            soldNum = (TextView) itemView.findViewById(R.id.fragment_food_list_item_buynum);

            image = (ImageView) itemView.findViewById(R.id.fragment_food_list_item_showiamge);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }
}
