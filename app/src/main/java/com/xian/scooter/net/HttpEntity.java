package com.xian.scooter.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * http数据接收
 */
public class HttpEntity implements Parcelable {

    @JSONField(name = "status")
    private int status;//判断请求是否成功
    @JSONField(name = "message")
    private String message;//错误信息
    @JSONField(name = "data")
    private String data;//需要的数据

    public  HttpEntity(){

    }

    protected HttpEntity(Parcel in) {
        status = in.readInt();
        message = in.readString();
        data = in.readString();
    }

    public static final Creator<HttpEntity> CREATOR = new Creator<HttpEntity>() {
        @Override
        public HttpEntity createFromParcel(Parcel in) {
            return new HttpEntity(in);
        }

        @Override
        public HttpEntity[] newArray(int size) {
            return new HttpEntity[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeString(message);
        parcel.writeString(data);
    }
}
