package com.phy0312.shopassistant.model;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class MainColumnGroup {

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
