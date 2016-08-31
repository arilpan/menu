package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.fragment.ShopFragment;
import com.xdkj.campus.menu.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class CommentFragment extends BaseFragment
{
    TextView comment;
    Button comment_button;

    public CommentFragment()
    {
        // Required empty public constructor
    }

    public static CommentFragment newInstance()
    {

        Bundle args = new Bundle();
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            dishes_id = args.getString("dish_id");
            user_id = args.getString("user_id");
            content = args.getString("content");
        }
    }

    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        comment = (TextView) view.findViewById(R.id.comment);
        comment_button = (Button) view.findViewById(R.id.comment_button);

        comment_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //提交后退出
                start(ShopFragment.newInstance(ShopFragment.shop_id));
                EventBus.getDefault().post(
                        new NetworkEvent(RequestType.ORDER_COMMENT));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "WaterFall 你调用咩?");
        if (RequestType.ORDER_COMMENT == event.reqType)
        {
            Log.e("arilpan", "WaterFall equals url="
                    + event.url + event.id);
            /**
             *
             public static String dish_comment_url = BASE_PROJECT_URL +
             "appEvaluation/saveAddEvaluations.do?user_id=USERID" +
             "&dishes_id=DISHID&content=CONTENT";
             */

//           String url="http://172.16.0.75:8080/GrogshopSystem/appShop/shop_dishes_info" +
//                    ".do?iDisplayStart=0&iDisplayLength=10&org_id=ba262eba-05da-4886-947c" +
//                    "-5a557c954af5";
            String url = event.url.replace("USERID", user_id).replace("DISHID", dishes_id)
                    .replace("CONTENT", content);
            if (getData(url))
            {
                setData();
            }
        } else
        {
            Log.e("arilpan", "WaterFall what happend?");
        }
    }

    public boolean getData(String url)
    {
        try
        {
            //network
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String res = body.string();

            //result parse
            JSONObject jsonObject = new JSONObject(res);
            boolean isSuccess = jsonObject.getBoolean("success");
            if (isSuccess)
            {
                Toast.makeText(getContext(),
                        "评价成功", Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Toast.makeText(getContext(),
                "评价失败", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    public void setData()
    {
        _mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                findChildFragment(CommentFragment.class).pop();
                //stuff that updates ui
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
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    String user_id;
    String dishes_id;
    String content;
}
