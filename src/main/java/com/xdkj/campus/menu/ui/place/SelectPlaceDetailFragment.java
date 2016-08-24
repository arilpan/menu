package com.xdkj.campus.menu.ui.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.bigkoo.pickerview.TimePickerView;
import com.xdkj.campus.menu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Create/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class SelectPlaceDetailFragment extends SupportFragment
        implements IListDialogListener, ISimpleDialogCancelListener
{
    private static final String ARG_NUMBER = "arg_number";
    private int mNumber;

    public static SelectPlaceDetailFragment newInstance(int number)
    {
        SelectPlaceDetailFragment fragment = new SelectPlaceDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);
        initView(view);
        return view;
    }

    //pre_order_list_small_rl
    // 用餐人数
    // 用餐时间
    // 包间
    // 选择菜品
    RelativeLayout orderPersonNumRl;
    RelativeLayout orderTimeRl;
    RelativeLayout orderRoomRl;
    RelativeLayout orderSelectDishRl;
    TextView select_room;
    TextView select_time;
    TextView select_person_num;

    private void initView(View view)
    {
        orderPersonNumRl = (RelativeLayout) view.findViewById(R.id.order_person_num);
        orderTimeRl = (RelativeLayout) view.findViewById(R.id.order_time);
        orderRoomRl = (RelativeLayout) view.findViewById(R.id.order_room);
        orderSelectDishRl = (RelativeLayout) view.findViewById(R.id.order_dishes);

        select_room = (TextView) view.findViewById(R.id.select_room);
        select_person_num = (TextView) view.findViewById(R.id.select_num);
        select_time = (TextView) view.findViewById(R.id.select_time);


        orderSelectDishRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择菜品
            }
        });
        orderRoomRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用选择房间
                ListDialogFragment
                        .createBuilder(getContext(), getFragmentManager())
                        .setTitle("Your favorite character:")
                        .setItems(new String[]{"地意见", "第二见", "第三见",
                                "第四", "物件", "六间"})
                        .setTargetFragment(SelectPlaceDetailFragment.this, SELECT_ROOM)
                        .show();
            }
        });
        orderPersonNumRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //调用填写人数
                //调用选择房间
                ListDialogFragment
                        .createBuilder(getContext(), getFragmentManager())
                        .setTitle("Your favorite character:")
                        .setItems(new String[]{"1", "2", "3",
                                "4", "5", "6"})
                        .setTargetFragment(SelectPlaceDetailFragment.this, SELECT_PERSON_NUM)
                        .show();
            }
        });
        vMasker = (View) view.findViewById(R.id.vMasker);
        initTimeSelect();
        orderTimeRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //弹出时间选择器
                pvTime.show();
            }
        });

    }

    //time start

    TimePickerView pvTime;
    View vMasker;

    public void initTimeSelect()
    {
        //调用选择时间
        //时间选择器
        pvTime = new TimePickerView(getContext(), TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.YEAR) + 1);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(Date date)
            {
//                        tvTime.setText(getTime(date));
                Log.e("arilpan", getTime(date));
                select_time.setText(getTime(date));
            }
        });
    }

    public static String getTime(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        if (pvTime.isShowing())
        {
            pvTime.dismiss();
            return true;
        }
        return super.onBackPressedSupport();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//
//            if (pvTime.isShowing())
//            {
//                pvTime.dismiss();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //time end

    private static final int SELECT_PERSON_NUM = 11;
    private static final int SELECT_TIME = 10;
    private static final int SELECT_ROOM = 9;

    @Override
    public void onListItemSelected(CharSequence value, int number, int requestCode)
    {
        if (requestCode == SELECT_ROOM)
        {
            select_room.setText(value);
        }
        if (requestCode == SELECT_PERSON_NUM)
        {
            select_person_num.setText(value);
        }
    }

    @Override
    public void onCancelled(int requestCode)
    {
        switch (requestCode)
        {
            case SELECT_TIME:
                Toast.makeText(getContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                break;
            case SELECT_ROOM:
            case SELECT_PERSON_NUM:
                Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
                break;
//            case REQUEST_DATE_PICKER:
//                Toast.makeText(c, "Date picker cancelled", Toast.LENGTH_SHORT).show();
//                break;
//            case REQUEST_TIME_PICKER:
//                Toast.makeText(c, "Time picker cancelled", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

}
