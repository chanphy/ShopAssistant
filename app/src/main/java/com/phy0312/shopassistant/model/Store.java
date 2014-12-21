package com.phy0312.shopassistant.model;


import android.os.Parcel;
import android.os.Parcelable;


public class Store implements Parcelable {

    /**
     * 商家ID
     */
    private String StoreId;
    /**
     * 商场ID
     */
    private String PlazaId;
    /**
     * 类型 0:普通 1:美食
     */
    private Integer category;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 人均消费
     */
    private Integer averageCost;
    /**
     * 商家Icon地址
     */
    private String icon;
    /**
     * 商家电话
     */
    private String telephone;
    /**
     * 商家所属楼层
     */
    private Integer floor;
    /**
     * 商家状态(预留)
     */
    private Integer status;
    /**
     * 商家描述
     */
    private String description;

    public Store() {
    }


    public Store(String StoreId, String PlazaId, Integer Category, String Name, String Address, Integer AverageCost, String Icon, String Telephone, Integer Floor,
                 Integer Status, String Description) {
        this.StoreId = StoreId;
        this.PlazaId = PlazaId;
        this.category = Category;
        this.name = Name;
        this.address = Address;
        this.averageCost = AverageCost;
        this.icon = Icon;
        this.telephone = Telephone;
        this.floor = Floor;
        this.status = Status;
        this.description = Description;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String StoreId) {
        this.StoreId = StoreId;
    }

    public String getPlazaId() {
        return PlazaId;
    }

    public void setPlazaId(String PlazaId) {
        this.PlazaId = PlazaId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer Category) {
        this.category = Category;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }

    public Integer getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Integer AverageCost) {
        this.averageCost = AverageCost;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String Icon) {
        this.icon = Icon;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String Telephone) {
        this.telephone = Telephone;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer Floor) {
        this.floor = Floor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer Status) {
        this.status = Status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.StoreId);
        dest.writeString(this.PlazaId);
        dest.writeValue(this.category);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeValue(this.averageCost);
        dest.writeString(this.icon);
        dest.writeString(this.telephone);
        dest.writeValue(this.floor);
        dest.writeValue(this.status);
        dest.writeString(this.description);
    }

    private Store(Parcel in) {
        this.StoreId = in.readString();
        this.PlazaId = in.readString();
        this.category = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.address = in.readString();
        this.averageCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.icon = in.readString();
        this.telephone = in.readString();
        this.floor = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.description = in.readString();
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        public Store createFromParcel(Parcel source) {
            return new Store(source);
        }

        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
}
