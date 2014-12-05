package com.phy0312.shopassistant.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Entity mapped to table HUO_DONG.
 */
public class HuoDong implements Parcelable{

    private Long id;
    private String HuoDongId;
    private String StoreId;
    private Long StartTime;
    private Long EndTime;
    private Integer Category;
    private String Description;
    private String Name;
    private String Icon;
    private Integer Status;
    private java.util.Date CreateTime;

    public HuoDong() {
    }

    public HuoDong(Long id) {
        this.id = id;
    }


    public HuoDong(Long id, String huoDongId, String storeId, Long startTime, Long endTime, Integer category, String description, String name, String icon, Integer status) {
        this.id = id;
        HuoDongId = huoDongId;
        StoreId = storeId;
        StartTime = startTime;
        EndTime = endTime;
        Category = category;
        Description = description;
        Name = name;
        Icon = icon;
        Status = status;
    }

    public HuoDong(Long id, String HuoDongId, String StoreId, Long StartTime, Long EndTime, Integer Category, String Description, String Name, String Icon, Integer Status, java.util.Date CreateTime) {
        this.id = id;
        this.HuoDongId = HuoDongId;
        this.StoreId = StoreId;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Category = Category;
        this.Description = Description;
        this.Name = Name;
        this.Icon = Icon;
        this.Status = Status;
        this.CreateTime = CreateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public java.util.Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(java.util.Date CreateTime) {
        this.CreateTime = CreateTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.HuoDongId);
        dest.writeString(this.StoreId);
        dest.writeValue(this.StartTime);
        dest.writeValue(this.EndTime);
        dest.writeValue(this.Category);
        dest.writeString(this.Description);
        dest.writeString(this.Name);
        dest.writeString(this.Icon);
        dest.writeValue(this.Status);
        dest.writeLong(CreateTime != null ? CreateTime.getTime() : -1);
    }

    private HuoDong(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.HuoDongId = in.readString();
        this.StoreId = in.readString();
        this.StartTime = (Long) in.readValue(Long.class.getClassLoader());
        this.EndTime = (Long) in.readValue(Long.class.getClassLoader());
        this.Category = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Description = in.readString();
        this.Name = in.readString();
        this.Icon = in.readString();
        this.Status = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpCreateTime = in.readLong();
        this.CreateTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
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
