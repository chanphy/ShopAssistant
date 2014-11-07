package com.phy0312.shopassistant.model;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class MainColumnGroup {

    public static final int CATEGORY_WEEKRECOMMEND = 0;
    public static final int CATEGORY_COUPON = 1;
    public static final int CATEGOTY_HUODONG = 2;
    public static final int CATEGOTY_FLIM = 3;

    private MainColumnInfo[] mainColumnInfos;

    public MainColumnGroup(MainColumnInfo[] mainColumnInfos, int category) {
        this.mainColumnInfos = mainColumnInfos;
        this.category = category;
    }


    private int category;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setMainColumnInfos(MainColumnInfo[] mainColumnInfos) {
        this.mainColumnInfos = mainColumnInfos;
    }

    public MainColumnInfo[] getMainColumnInfos() {
        return mainColumnInfos;
    }
}
