package com.xdkj.campus.menu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.News;
import com.xdkj.campus.menu.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arilpan@qq.com on 16/8.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder>
{
    private List<News> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public NewsListAdapter(Context context)
    {
        this.mInflater = LayoutInflater.from(context);

    }

    public void setDatas(List<News> items)
    {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = mInflater.inflate(R.layout.fragment_dish_news_list_item, parent, false);
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
        News item = mItems.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        //todo : load image
        holder.image.setImageResource(R.drawable.index_dishes_image_default);
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView content;

        private ImageView image;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);

            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }
}
