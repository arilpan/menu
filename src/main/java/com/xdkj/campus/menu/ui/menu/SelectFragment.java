package com.xdkj.campus.menu.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.fragment.ShopFragment;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.ShopEvent;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 右侧选择菜品的GridView
 *
 * @Author arilpan
 */
public class SelectFragment extends BaseFragment
{
    private static final String ARG_PARAM1 = "select_dish";
    GridView select_grid;
    String classname;

    public SelectFragment()
    {
        // Required empty public constructor
    }

    public static SelectFragment newInstance(String param1, String param2)
    {
        SelectFragment fragment = new SelectFragment();
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
        View view = inflater.inflate(R.layout.fragment_select, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {

        TextView tvClassname = (TextView) view.findViewById(R.id.class_name);
        tvClassname.setText(classname);

        select_grid = (GridView) view.findViewById(R.id.select_grid);
    }



    LinearLayout select_dish_layout;
    ArrayList<Dish> item;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        int[] resIds = {R.drawable.index_dishes_image_default,
                R.drawable.index_dishes_image_default,
                R.drawable.index_dishes_image_default,
                R.drawable.index_dishes_image_default};
//        String[] name = {"某菜1", "某菜2", "某菜3", "某菜4"};
        String[] name = {"土豆丝", "青菜", "小白菜", "茄子"};
        String[] price = {"52", "48", "22", "92"};
        String pre_order = "预约";
        item = new ArrayList<Dish>();
        for (int i = 0; i < resIds.length; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item_image", resIds[i]);
            map.put("item_right", pre_order);
            Dish dish = new Dish();
            dish.setName(name[i]);
            dish.setPrice("$"+price[i]);
            dish.setPreOrder("预约");
            dish.setImg(R.drawable.index_dishes_image_default);
            item.add(dish);
        }
      /*  SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), item,
                R.layout.fragment_select_dishes_grid_item, new String[]
                {"item_image", "item_left", "item_middle", "item_right"},
                new int[]{R.id.item_image, R.id.item_left, R.id.item_middle,
                        R.id.item_right}) {
        };*/
        DishGridAdapter dga = new DishGridAdapter(
                item, R.layout.fragment_select_dishes_grid_item, getContext());
        select_grid.setAdapter(dga);
//        select_grid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("arilpan", "选择菜品的item setOnClickListener+" + v.getId());
//            }
//        });
        select_grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.e("arilpan", "选择菜品的item setOnItemClickListener +"
                        + view.getId() + ",position " + position);
                //加入菜品信息到列表
                Dish dish = item.get(position);
                dish.setNum(dish.getNum() + 1);
                DishList.getlist().remove(dish);
                DishList.getlist().add(dish);
                EventBus.getDefault().post(new ShopEvent(ShopFragment.newInstance()));
//               switchDishListFragment(DishListFragment.newInstance(null, null));
                ((ShopFragment) getParentFragment()).switchDishListFragment();
            }
        });
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

    class DishGridAdapter extends BaseAdapter
    {
        private List<Dish> dishs;//ListView显示的数据

        private int resource;//显示列表项的Layout

        private LayoutInflater inflater;//界面生成器

        private Context context;

        class ViewHolder
        {
        }

        DishGridAdapter(List dishs, int resource, Context context)
        {
            this.dishs = dishs;
            this.resource = resource;
            this.context = context.getApplicationContext();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount()
        {
            return dishs.size();
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = inflater.inflate(resource, null);
            }

            ImageView dish_img = (ImageView) convertView.findViewById(R.id.item_image);
            TextView dish_name = (TextView) convertView.findViewById(R.id.item_left);
            TextView dish_desc = (TextView) convertView.findViewById(R.id.item_middle);
            TextView dish_order_btn = (TextView) convertView.findViewById(R.id.item_right);

            Dish dish = dishs.get(position);
            dish_name.setText(dish.getName());
            dish_desc.setText(dish.getPrice());
            dish_order_btn.setText("预约");

            dish_img.setImageResource(R.drawable.index_dishes_image_default);

            dish_img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.e("arilpan", "选择菜品的item Adapter dish_img +"
                            + view.getId());
                    SupportFragment sf = findChildFragment(DishDetailFragment.class);
                    if (sf != null)
                    {
                        sf.pop();

                        Log.e("arilpan", "findChildFragment不空 已经pop");
                    }
                    sf = findFragment(DishDetailFragment.class);
                    if (sf != null)
                    {
                        sf.pop();
                        Log.e("arilpan", "findFragment 已经pop");
                    }
                    EventBus.getDefault().post(DishDetailFragment.newInstance(position));
//                    start(DishDetailFragment.newInstance(1));
                    //  Toast.makeText(context, good.getGoodProvider(),
                    //  Toast.LENGTH_LONG).show();
                }

            });
            return convertView;
        }
    }
}
