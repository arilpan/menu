package com.xdkj.campus.menu;

/**
 * Created by aril_pan@qq.com on 16-8-23.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkTest extends Activity
{
    private static final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<List<Contributor>> CONTRIBUTORS_JSON_ADAPTER = MOSHI.adapter(
            Types.newParameterizedType(List.class, Contributor.class));

    static class Contributor
    {
        String login;
        int contributions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        EventBus.getDefault().register(NetworkTest.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_test_main);
        EventBus.getDefault().post(new networkEvent());
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(NetworkTest.this);
        super.onDestroy();
    }

    class networkEvent
    {

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(networkEvent event)
    {
        Log.e("arilpan", "哥 你调用咩?");
        getData();
    }

    public void getData()
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            List<Contributor> contributors =
                    CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());
            body.close();
            Collections.sort(contributors, new Comparator<Contributor>()
            {
                @Override
                public int compare(Contributor c1, Contributor c2)
                {
                    return c2.contributions - c1.contributions;
                }
            });
            Log.e("arilpan", "res : " + body.toString());
            for (Contributor contributor : contributors)
            {
                Log.e("arilpan", contributor.login + ": " + contributor.contributions);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws Exception
    {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        List<Contributor> contributors = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());
        body.close();
        Collections.sort(contributors, new Comparator<Contributor>()
        {
            @Override
            public int compare(Contributor c1, Contributor c2)
            {
                return c2.contributions - c1.contributions;
            }
        });
        Log.e("arilpan", "res : " + body.toString());
        for (Contributor contributor : contributors)
        {
            Log.e("arilpan", contributor.login + ": " + contributor.contributions);
        }
    }

    public NetworkTest()
    {
        // No instances.
    }
}