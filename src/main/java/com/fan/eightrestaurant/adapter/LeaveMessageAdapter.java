package com.fan.eightrestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.fan.eightrestaurant.bean.LeaveMessage;

import java.util.List;

/**
 * Created by xdkj on 2016/8/11.
 */
public class LeaveMessageAdapter extends BaseAdapter {

    private Context context;
    private List<LeaveMessage> list;

    public LeaveMessageAdapter(Context context, List<LeaveMessage> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_leave_message_list_item,null);
            viewHolder.ask = (TextView) convertView.findViewById(R.id.leave_message_item_ask);
            viewHolder.answer = (TextView) convertView.findViewById(R.id.leave_message_item_answer);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ask.setText(list.get(position).getM_title());
        viewHolder.answer.setText(list.get(position).getM_content());

        return convertView;
    }
    class ViewHolder{
        TextView ask,answer;
    }
}
