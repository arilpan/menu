package com.xdkj.campus.menu.child;

import com.xdkj.campus.menu.entity.Dish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdkj on 2016/8/11.
 */
public class DishList {
    public static List<Dish> dishes;

    public static List getlist() {
        if (dishes == null) {
            dishes = new ArrayList<>();
        }
        return dishes;
    }

    public static int getTotalPrice() {
        int total = 0;
        dishes = getlist();
        for (Dish dish : dishes) {
            total = total + (dish.getNum() * Integer.parseInt(dish.getPrice()));
        }
        return total;
    }
}
