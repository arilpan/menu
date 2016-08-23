package com.xdkj.campus.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.model.IPickerViewData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aril_pan@qq.com on 16-8-17.
 */
public class Demo3Activity extends Activity
{
    private TextView tvTime, tvOptions;
    TimePickerView pvTime;
    View vMasker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_test_main);

        vMasker = findViewById(R.id.vMasker);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvOptions = (TextView) findViewById(R.id.tvOptions);
        tvOptions.setVisibility(View.GONE);

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(Date date)
            {
                tvTime.setText(getTime(date));
            }
        });
        //弹出时间选择器
        tvTime.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                pvTime.show();
            }
        });


    }

    public static String getTime(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {

            if (pvTime.isShowing())
            {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}