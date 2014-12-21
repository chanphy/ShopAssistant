package com.phy0312.shopassistant.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Coupon implements Parcelable {

    /**
     * 优惠券ID
     */
    private String couponId;
    /**
     * 所属的商家
     */
    private String storeId;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券开始时间
     */
    private Long StartTime;
    /**
     * 优惠券结束时间
     */
    private Long EndTime;
    /**
     * 优惠券类型
     */
    private Integer Category;
    /**
     * 优惠券描述
     */
    private String Description;
    /**
     * 优惠券缩略图
     */
    private String icon;
    /**
     * 优惠券数量
     */
    private int count;

    public Coupon() {
    }

    public Coupon(String CouponId, String StoreId, String Name, Long StartTime, Long EndTime,
                  Integer Category, String Description,
                  String iconPath, int count) {
        this.couponId = CouponId;
        this.storeId = StoreId;
        this.name = Name;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Category = Category;
        this.Description = Description;
        this.icon = iconPath;
        this.count = count;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String CouponId) {
        this.couponId = CouponId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String StoreId) {
        this.storeId = StoreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public Long getStartTime() {
        return StartTime;
    }

    public void setStartTime(Long StartTime) {
        this.StartTime = StartTime;
    }

    public Long getEndTime() {
        return EndTime;
    }

    public void setEndTime(Long EndTime) {
        this.EndTime = EndTime;
    }

    public Integer getCategory() {
        return Category;
    }

    public void setCategory(Integer Category) {
        this.Category = Category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.couponId);
        dest.writeString(this.storeId);
        dest.writeString(this.name);
        dest.writeValue(this.StartTime);
        dest.writeValue(this.EndTime);
        dest.writeValue(this.Category);
        dest.writeString(this.Description);
        dest.writeString(this.icon);
        dest.writeInt(this.count);
    }

    private Coupon(Parcel in) {
        this.couponId = in.readString();
        this.storeId = in.readString();
        this.name = in.readString();
        this.StartTime = (Long) in.readValue(Long.class.getClassLoader());
        this.EndTime = (Long) in.readValue(Long.class.getClassLoader());
        this.Category = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Description = in.readString();
        this.icon = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
