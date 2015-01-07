package com.phy0312.shopassistant.data;

import com.phy0312.shopassistant.model.Plaza;
import com.phy0312.shopassistant.model.Product;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.model.Coupon;
import com.phy0312.shopassistant.model.HuoDong;
import com.phy0312.shopassistant.model.Store;
import com.phy0312.shopassistant.model.MainColumnGroup;
import com.phy0312.shopassistant.model.MainColumnInfo;
import com.phy0312.shopassistant.ui.base.UIUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: 数据中心<br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class DataManager {

    /**
     * 获取首页数据结构
     *
     * @return
     */
    public static List<MainColumnGroup> GetMainColumnInfos() {

        List<MainColumnGroup> list = new ArrayList<MainColumnGroup>();

        MainColumnInfo[] recommendColumns = new MainColumnInfo[3];
        recommendColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 1, "优惠券1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        recommendColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 2, "活动", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        recommendColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_PRODUCT, 3, "商品", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup recommendGroup = new MainColumnGroup(recommendColumns, Constants.CATEGORY_WEEKRECOMMEND);
        list.add(recommendGroup);


        MainColumnInfo[] couponColumns = new MainColumnInfo[3];
        couponColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 1, "优惠券1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        couponColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 2, "优惠券2", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        couponColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_COUPON, 3, "优惠券3", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup couponGroup = new MainColumnGroup(couponColumns, Constants.CATEGORY_COUPON);
        list.add(couponGroup);

        MainColumnInfo[] huodongColumns = new MainColumnInfo[3];
        huodongColumns[0] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 1, "活动1", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        huodongColumns[1] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 2, "活动2", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        huodongColumns[2] = new MainColumnInfo(MainColumnInfo.TYPE_HUODONG, 3, "活动3", "", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        MainColumnGroup huodongGroup = new MainColumnGroup(huodongColumns, Constants.CATEGOTY_ACTIVITY);
        list.add(huodongGroup);

        return list;
    }

    public static List<HuoDong> getHuoDongs(int type) {
        List<HuoDong> huoDongs = new ArrayList<HuoDong>();

        HuoDong huoDong1 = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        HuoDong huoDong2 = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        HuoDong huoDong3 = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        HuoDong huoDong4 = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        HuoDong huoDong5 = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);

        huoDongs.add(huoDong1);
        huoDongs.add(huoDong2);
        huoDongs.add(huoDong3);
        huoDongs.add(huoDong4);
        huoDongs.add(huoDong5);

        return huoDongs;
    }

    public static List<Coupon> getCoupons() {
        List<Coupon> coupons = new ArrayList<Coupon>();

        Coupon coupon1 = new Coupon("1", "1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述",  "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);
        Coupon coupon2 = new Coupon("1", "1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);
        Coupon coupon3 = new Coupon("1","1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述","http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);
        Coupon coupon4 = new Coupon("1", "1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);
        Coupon coupon5 = new Coupon( "1", "1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述",  "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);

        coupons.add(coupon1);
        coupons.add(coupon2);
        coupons.add(coupon3);
        coupons.add(coupon4);
        coupons.add(coupon5);

        return coupons;
    }


    /**
     * 获取美食商家的信息
     * @return
     */
    public static List<Store> getFoodStores() {
        List<Store> stores = new ArrayList<Store>();

        Store store = new Store("1", "1", 1, "大丰收","3F-306", 75, "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", "0591-83956234", 3, 1, "年年大丰收");
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        stores.add(store);
        return stores;
    }


    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();

        Product product = new Product("1", "太平鸟男装2014新款",1, 1000,880,"太平鸟男装", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");

        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);

        return products;
    }

    public static List<Plaza> getPlazas() {
        List<Plaza> plazas = new ArrayList<>();
        Plaza plaza = new Plaza("福州", new Date(), 26.086944, 119.320215, "东方百货", "db", "100001", "", "059183570737", 1);
        plazas.add(plaza);
        return plazas;
    }

    public static Plaza getPlazaById(String id) {
        Plaza plaza = new Plaza("福州", new Date(), 26.086944, 119.320215, "东方百货", "db", "100001", "", "059183570737", 1);
        return plaza;
    }


    public static HuoDong getHuoDongById(String id) {
        HuoDong huoDong = new HuoDong("1", "1",  System.currentTimeMillis(), System.currentTimeMillis(), Constants.HOT,  "", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        return huoDong;
    }

    public static Coupon getCouponById(String id) {
        Coupon coupon = new Coupon("1", "1","优惠券", System.currentTimeMillis(),
                System.currentTimeMillis(), 0, "优惠券描述",  "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 100);
        return coupon;
    }

    public static Product getProductById(String id) {
        Product product = new Product("1", "太平鸟男装2014新款",1, 1000,880,"太平鸟男装", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg");
        return product;
    }

    public static Store getStoreById(String id) {
        Store store = new Store("1", "1", 1, "大丰收","3F-306", 75, "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", "0591-83956234", 3, 1, "年年大丰收");
        return store;
    }

    public static List<UIUtil.AdsItemType> getAdsItemTypes() {
        UIUtil.AdsItemType couponItemType = new UIUtil.AdsItemType();
        couponItemType.iconUrl = "http://jingdongquan.net/sites/default/files/imagecache/512/auto_img/2012/10/31/20121031124351.jpg";
        couponItemType.type = Constants.CATEGORY_COUPON;
        couponItemType.id = "1";

        UIUtil.AdsItemType activityItemType = new UIUtil.AdsItemType();
        activityItemType.iconUrl = "http://www.yihaodianquan.com/sites/yihaodianquan.com/files/imagecache/512/2010/12/jizhongri-youhuiquan.jpg";
        activityItemType.type = Constants.CATEGOTY_ACTIVITY;
        activityItemType.id = "1";

        UIUtil.AdsItemType productItemType = new UIUtil.AdsItemType();
        productItemType.iconUrl = "http://jingdongquan.net/sites/default/files/imagecache/512/2011/10/360buy-baihuo.jpg";
        productItemType.type = Constants.CATEGOTY_PRODUCT;
        couponItemType.id = "1";

        List<UIUtil.AdsItemType> list = new ArrayList<>();

        list.add(couponItemType);
        list.add(activityItemType);
        list.add(productItemType);

        return list;
    }

}
