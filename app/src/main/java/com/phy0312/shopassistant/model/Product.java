package com.phy0312.shopassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description: <br/>
 * author: dingdj<br/>
 * data: 2014/12/4<br/>
 */
public class Product implements Parcelable{

    private String productId;

    private String productName;

    private int productCategory;

    private int productPrice;

    private int productDiscountPrice;

    private String productBrand;

    private String iconUrl;

    public Product(String productId, String productName, int productCategory, int productPrice, int productDiscountPrice, String productBrand, String iconUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.productBrand = productBrand;
        this.iconUrl = iconUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(int productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productName);
        dest.writeInt(this.productCategory);
        dest.writeInt(this.productPrice);
        dest.writeInt(this.productDiscountPrice);
        dest.writeString(this.productBrand);
        dest.writeString(this.iconUrl);
    }

    private Product(Parcel in) {
        this.productId = in.readString();
        this.productName = in.readString();
        this.productCategory = in.readInt();
        this.productPrice = in.readInt();
        this.productDiscountPrice = in.readInt();
        this.productBrand = in.readString();
        this.iconUrl = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
