package com.xdkj.campus.menu.event;

import com.squareup.moshi.JsonAdapter;
import com.xdkj.campus.menu.entity.RequestType;

import java.util.List;
import java.util.Objects;

/**
 * Created by aril_pan@qq.com on 16-8-23.
 */
public class NetworkEvent
{
    public String url;
    public int reqType;

    public NetworkEvent()
    {

    }

    //    JsonAdapter<List<Objects>> mode;
    public NetworkEvent(String url, int reqType)
    {
        this.url = url;
        this.reqType = reqType;
    }

}
