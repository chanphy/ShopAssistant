package com.phy0312.shopassistant.model;


public class Plaza {

    /**
     * 商场ID
     */
    private String PlazaId;

    /**
     * 城市ID
     */
    private String CityId;

    /**
     * 创建时间
     */
    private java.util.Date CreateTime;

    private Double Latitude;
    private Double Longitude;

    /**
     * 商场名称
     */
    private String Name;

    /**
     * 商场拼音
     */
    private String PinYin;

    /**
     * 地点
     */
    private String Address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 类型 备用
     */
    private Integer Type;

    public Plaza() {
    }


    public Plaza(String CityId, java.util.Date CreateTime, Double Latitude, Double Longitude, String Name, String PinYin, String PlazaId, String Address, String phone, Integer Type) {
        this.CityId = CityId;
        this.CreateTime = CreateTime;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Name = Name;
        this.PinYin = PinYin;
        this.PlazaId = PlazaId;
        this.Address = Address;
        this.phone = phone;
        this.Type = Type;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String CityId) {
        this.CityId = CityId;
    }

    public java.util.Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(java.util.Date CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
    }

    public String getPlazaId() {
        return PlazaId;
    }

    public void setPlazaId(String PlazaId) {
        this.PlazaId = PlazaId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer Type) {
        this.Type = Type;
    }

}
