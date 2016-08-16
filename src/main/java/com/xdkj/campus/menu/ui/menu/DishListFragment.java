package com.xdkj.campus.menu.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 下测查看已经选择的菜品信息
 *
 * @Author arilpan
 */
public class DishListFragment extends BaseFragment
{
    private static final String ARG_PARAM1 = "select_dish";
    ListView select_grid;
    String classname;

    public DishListFragment()
    {
        // Required empty public constructor
    }

    static DishListFragment fragment;

    public static DishListFragment newInstance(String param1, String param2)
    {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            classname = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_dishes, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {

        TextView tvClassname = (TextView) view.findViewById(R.id.class_name);
        tvClassname.setText(classname);
        select_grid = (ListView) view.findViewById(R.id.select_grid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initGridView();
    }

    public void initGridView()
    {
        List<Dish> items = DishList.getlist();
//        String[] name = {"土豆丝", "青菜", "小白菜", "茄子"};
//        int[] price = {12, 33, 54, 21};
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < items.size(); i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("item_image", resIds[i]);
            Dish dish_item = items.get(i);
            map.put("item_image", dish_item.getName());
            map.put("item_price", dish_item.getPrice());
            map.put("item_left", "+");
            map.put("item_middle", dish_item.getNum());
            map.put("item_right", "-");
            item.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), item,
                R.layout.fragment_select_dishes_list_item, new String[]
                {"item_image", "item_price", "item_left", "item_middle", "item_right"},
                new int[]{R.id.dish_name, R.id.dish_price,
                        R.id.item_left, R.id.item_middle, R.id.item_right})
        {
        };
        select_grid.setAdapter(simpleAdapter);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
