package com.xdkj.campus.menu.entity;

/**
 * Created by aril_pan@qq.com on 16-8-23.
 */
public class RequestType
{
    //TEMP
    public final static int LOGIN = 1;
    public final static int REGISTE = 1;

    //order
    public final static int ORDER_CANCEL_LIST = 11;
    public final static int ORDER_COMPLETE_LIST = 12;
    public final static int ORDER_UNCOMPLETE_LIST = 13;
    public final static int ORDER_DETAIL = 14;

    //index-dish
    public final static int INDEX_DISH_NEW = 31;
    public final static int INDEX_DISH_RANK = 32;
    public final static int INDEX_DISH_DISCOUNT = 33;
    public final static int INDEX_DISH_HOT = 34;

    //news
    public final static int NEWS_LIST = 41;
    public final static int NEWS_DETAIL = 42;

    //discount
    public final static int DISCOUNT_LIST = 51;
    public final static int DISCOUNT_DETAIL = 52;

    //action
    public final static int ORDER = 91;
    public final static int ORDER_CANCEL = 92;
    public final static int ORDER_COMMENT = 93;
}
