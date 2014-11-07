package com.phy0312.shopassistant.data;

import com.phy0312.shopassistant.model.MainColumnGroup;
import com.phy0312.shopassistant.model.MainColumnInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 数据中心<br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class DataManager {

    /**
     * 获取首页数据结构
     * @return
     */
    public static List<MainColumnGroup> GetMainColumnInfos(){

        List<MainColumnGroup> list = new ArrayList<MainColumnGroup>();

        MainColumnInfo[] recommendColumns = new MainColumnInfo[3];
        recommendColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 1, "优惠券1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        recommendColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 2, "活动", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        recommendColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 3, "活动2", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup recommendGroup = new MainColumnGroup(recommendColumns, MainColumnGroup.CATEGORY_WEEKRECOMMEND);
        list.add(recommendGroup);


        MainColumnInfo[] couponColumns = new MainColumnInfo[3];
        couponColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 1, "优惠券1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        couponColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 2, "优惠券2", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        couponColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 3, "优惠券3", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup couponGroup = new MainColumnGroup(couponColumns, MainColumnGroup.CATEGORY_COUPON);
        list.add(couponGroup);

        MainColumnInfo[] huodongColumns = new MainColumnInfo[3];
        huodongColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 1, "活动1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        huodongColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 2, "活动2", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        huodongColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 3, "活动3", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup huodongGroup = new MainColumnGroup(huodongColumns, MainColumnGroup.CATEGOTY_HUODONG);
        list.add(huodongGroup);

        return list;
    }
}
