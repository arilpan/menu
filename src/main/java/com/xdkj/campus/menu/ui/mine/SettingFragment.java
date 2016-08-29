package com.xdkj.campus.menu.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * todo : add button logical
 * Created by aril_pan@qq.com on 16/8.
 */
public class SettingFragment extends BaseFragment
        implements ISimpleDialogListener
{
    int REQUEST_CLEAR_DATA = 100;

    public SettingFragment()
    {
    }

    public static SettingFragment newInstance()
    {

        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    LinearLayout ll_update;
    LinearLayout ll_clear_data;
    LinearLayout ll_about;
    LinearLayout ll_repswd;


    private void initView(View view)
    {
        ll_update = (LinearLayout) view.findViewById(R.id.ll_update);
        ll_clear_data = (LinearLayout) view.findViewById(R.id.ll_clear_data);
        ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
        ll_repswd = (LinearLayout) view.findViewById(R.id.ll_repswd);
        ll_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //todo:dialog
                SimpleDialogFragment.createBuilder(getContext(),
                        getChildFragmentManager())
                        .setMessage("暂无新版本")
                        .show();

            }
        });
        ll_clear_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SimpleDialogFragment.createBuilder(getContext(),
                        getChildFragmentManager())
                        .setTargetFragment(SettingFragment.this, REQUEST_CLEAR_DATA)
                        .setMessage("请问需要清除数据吗?")
                        .setPositiveButtonText("清除")
                        .setNegativeButtonText("取消")
                        .show();
            }
        });
        ll_about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventBus.getDefault().post(new StartBrotherEvent(
                        DishDetailFragment.newInstance("")));
            }
        });
        ll_repswd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventBus.getDefault().post(new StartBrotherEvent(
                        DishDetailFragment.newInstance("")));
            }
        });

    }

    @Override
    public boolean onBackPressedSupport()
    {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }

    @Override
    public void onNegativeButtonClicked(int requestCode)
    {
        Log.e("arilpan", "onNegativeButtonClicked code :" + REQUEST_CLEAR_DATA);

    }

    @Override
    public void onNeutralButtonClicked(int requestCode)
    {
        Log.e("arilpan", "on  NeutralButtonClicked code :" + REQUEST_CLEAR_DATA);
    }

    @Override
    public void onPositiveButtonClicked(int requestCode)
    {
        if (requestCode == REQUEST_CLEAR_DATA)
        {
            deleteCache(getContext());
            Toast.makeText(getContext(), "清除成功 ", Toast.LENGTH_SHORT).show();
        }
    }

    public static void deleteCache(Context context)
    {
        try
        {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory())
            {
                deleteDir(dir);
            }
        } catch (Exception e)
        {
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
