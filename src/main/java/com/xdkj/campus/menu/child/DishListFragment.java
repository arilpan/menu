package com.xdkj.campus.menu.child;

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

import java.util.ArrayList;
import java.util.HashMap;

/**
 */
public class DishListFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "select_dish";
    ListView select_grid;
    String classname;

    public DishListFragment() {
        // Required empty public constructor
    }

    public static DishListFragment newInstance(String param1, String param2) {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            classname = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_dishes, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        TextView tvClassname = (TextView) view.findViewById(R.id.class_name);
        tvClassname.setText(classname);
        select_grid = (ListView) view.findViewById(R.id.select_grid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] resIds = {R.drawable.ic_account_circle_white_48dp,
                R.drawable.ic_account_circle_white_48dp,
                R.drawable.ic_account_circle_white_48dp,
                R.drawable.ic_account_circle_white_48dp};
        String[] name = {"某菜1", "某菜2", "某菜3", "某菜4"};
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < resIds.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item_image", resIds[i]);
            map.put("item_price", name[i]);
            map.put("item_left", name[i]);
            map.put("item_middle", name[i]);
            map.put("item_right", name[i]);
            item.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), item,
                R.layout.fragment_select_dishes_list_item, new String[]
                {"item_image", "item_price","item_left", "item_middle", "item_right"},
                new int[]{R.id.dish_name, R.id.dish_price,
                        R.id.item_left,   R.id.item_middle, R.id.item_right}) {
        };
        select_grid.setAdapter(simpleAdapter);
//        select_grid.setAdapter(new ListAdapter() {
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled(int position) {
//                return false;
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public int getItemViewType(int position) {
//                return 0;
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 0;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
