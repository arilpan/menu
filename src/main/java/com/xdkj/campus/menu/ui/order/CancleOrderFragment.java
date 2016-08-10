package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class CancleOrderFragment extends BaseFragment {

    public CancleOrderFragment() {
    }

    public static CancleOrderFragment newInstance() {
        Bundle args = new Bundle();
        CancleOrderFragment fragment = new CancleOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_cancel, container, false);
        initView(view);
        return view;
    }

    RecyclerView order_recyview;
    private HomeAdapter mAdapter;
    private List<String> mDatas;

    private void initView(View view) {

        order_recyview = (RecyclerView) view.findViewById(R.id.order_recyview);
        order_recyview.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
        ViewGroup.LayoutParams layoutParams = order_recyview.getLayoutParams();
        layoutParams.height = 600;
        order_recyview.setLayoutParams(layoutParams);
        order_recyview.setAdapter(mAdapter = new HomeAdapter());
//        basicParamInit();
//        initData();
//        initRecyclerView();
    }


    @Override
    public boolean onBackPressedSupport() {
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
//            holder.tv.setText(mDatas.get(position));
            holder.order_item_recyview.setMinimumHeight(400);
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            RecyclerView order_item_recyview  ;

            public MyViewHolder(View view)
            {
                super(view);
                view.setMinimumHeight(450);
                order_item_recyview   = (RecyclerView) view.findViewById(R.id.order_item_recyview);
//                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
