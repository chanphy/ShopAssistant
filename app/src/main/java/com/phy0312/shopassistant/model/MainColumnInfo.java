package com.phy0312.shopassistant.model;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class MainColumnInfo {


    public final static int TYPE_COUPON = 0;
    public final static int TYPE_HUODONG = 1;
    public final static int TYPE_PRODUCT = 2;

    private int type;
    private int ID;
    private String title;
    private String desc;
    private String Icon;
    private String price;


    public MainColumnInfo(int type, int ID, String title, String desc, String icon) {
        this.type = type;
        this.ID = ID;
        this.title = title;
        this.desc = desc;
        Icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
