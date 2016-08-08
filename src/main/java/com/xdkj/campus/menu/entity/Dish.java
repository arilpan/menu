package com.xdkj.campus.menu.entity;

/**
 * Created by xdkj on 2016/8/8.
 */
public class Dish {
    private int id;
    private String name;
    private String desc;
    private String price;

    public Dish(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
