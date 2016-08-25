package com.xdkj.campus.menu.event;

import com.squareup.moshi.JsonAdapter;
import com.xdkj.campus.menu.api.APIAddr;
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

    //JsonAdapter<List<Objects>> mode;
    public NetworkEvent(int  reqType)
    {
//        this.url = url;
        switch (reqType)
        {
            case RequestType.INDEX_ALL:
                this.url = APIAddr.index_url;
                break;
            case RequestType.INDEX_DISH_SELECT_LEFT:
                this.url = APIAddr.select_dish_left_url;
                break;
            case RequestType.INDEX_DISH_SELECT_RIGHT:
                this.url = APIAddr.select_dish_right_url;
                break;
            case RequestType.INDEX_DISH_RANK:
                this.url = APIAddr.dish_rank_url;
                break;
            case RequestType.INDEX_DISH_HOT:
                this.url = APIAddr.dish_hot_more_url;
                break;
            case RequestType.INDEX_DISH_DISCOUNT:
                this.url = APIAddr.dish_discount_url;
                break;
            case RequestType.INDEX_DISH_NEW:
                this.url = APIAddr.dish_new_to_taste_url;
                break;
            case RequestType.INDEX_DISH_SELECT_ROOM:
                this.url = APIAddr.dish_select_room_url;
                break;
            case RequestType.INDEX_DISH_DETAIL:
                this.url = APIAddr.dish_detail_url;
            case RequestType.DISCOUNT_LIST:
                this.url = APIAddr.recharge_discount_new_url;
                break;
//            case RequestType.INDEX_ALL:
//                this.url = APIAddr.shop_detail_url;
//                break;
        }
    }

}
