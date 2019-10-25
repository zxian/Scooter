package com.xian.scooter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 导航信息
 */
public class NavBean implements Parcelable {

    private String address;//地址
    private double longitude;//经度
    private double latitude;//纬度

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
    }

    public NavBean() {
    }

    protected NavBean(Parcel in) {
        this.address = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
    }

    public static final Creator<NavBean> CREATOR = new Creator<NavBean>() {
        @Override
        public NavBean createFromParcel(Parcel source) {
            return new NavBean(source);
        }

        @Override
        public NavBean[] newArray(int size) {
            return new NavBean[size];
        }
    };
}
