package com.xdkj.campus.menu.ui.menu;

import com.xdkj.campus.menu.entity.Dish;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择过的菜品的信息list
 * Created by arilpan on 2016/8/11.
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