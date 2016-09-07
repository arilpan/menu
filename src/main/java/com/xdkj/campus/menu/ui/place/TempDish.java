package com.xdkj.campus.menu.ui.place;

/**
 * Created by aril_pan@qq.com on 16-9-2.
 */
public class TempDish
{
    String dishes_name;
    String dishes_price;
    int dishes_count;
    String url;
    String dish_id;


    TempDish()
    {
    }

    TempDish(String dishes_name, String dishes_price, int dishes_count, String url, String dish_id)
    {
        this.dishes_price = dishes_price;
        this.dishes_name = dishes_name;
        this.dishes_count = dishes_count;
        this.url = url;
        this.dish_id = dish_id;
    }
}
