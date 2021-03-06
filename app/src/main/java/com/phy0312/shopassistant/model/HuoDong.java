package com.phy0312.shopassistant.model;

import android.os.Parcel;
import android.os.Parcelable;



public class HuoDong implements Parcelable{
    /**
     * 活动ID
     */
    private String HuoDongId;
    /**
     * 所属商家ID
     */
    private String StoreId;
    /**
     * 活动开始时间
     */
    private String StartTime;
    /**
     * 活动结束时间
     */
    private String EndTime;
    /**
     * 活动所属类型
     */
    private Integer Category;
    /**
     * 活动描述
     */
    private String Description;
    /**
     * 活动名称
     */
    private String Name;
    /**
     * 活动缩略图
     */
    private String IconPath;
    /**
     * 活动状态
     */
    private Integer Status;

    public HuoDong() {
    }


    public HuoDong(String huoDongId, String storeId, String startTime, String endTime, Integer category, String description, String name, String icon, Integer status) {
        HuoDongId = huoDongId;
        StoreId = storeId;
        StartTime = startTime;
        EndTime = endTime;
        Category = category;
        Description = description;
        Name = name;
        IconPath = icon;
        Status = status;
    }

    public String getHuoDongId() {
        return HuoDongId;
    }

    public void setHuoDongId(String HuoDongId) {
        this.HuoDongId = HuoDongId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String StoreId) {
        this.StoreId = StoreId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String Icon) {
        this.IconPath = Icon;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.HuoDongId);
        dest.writeString(this.StoreId);
        dest.writeValue(this.StartTime);
        dest.writeValue(this.EndTime);
        dest.writeValue(this.Category);
        dest.writeString(this.Description);
        dest.writeString(this.Name);
        dest.writeString(this.IconPath);
        dest.writeValue(this.Status);
    }

    private HuoDong(Parcel in) {
        this.HuoDongId = in.readString();
        this.StoreId = in.readString();
        this.StartTime = (String) in.readValue(String.class.getClassLoader());
        this.EndTime = (String) in.readValue(String.class.getClassLoader());
        this.Category = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Description = in.readString();
        this.Name = in.readString();
        this.IconPath = in.readString();
        this.Status = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpCreateTime = in.readLong();
    }

    public static final Creator<HuoDong> CREATOR = new Creator<HuoDong>() {
        public HuoDong createFromParcel(Parcel source) {
            return new HuoDong(source);
        }

        public HuoDong[] newArray(int size) {
            return new HuoDong[size];
        }
    };


}
