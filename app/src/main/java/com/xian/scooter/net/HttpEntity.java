package com.xian.scooter.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * http数据接收
 */
public class HttpEntity implements Parcelable {

    @JSONField(name = "code")
    private int code;//判断请求是否成功
    @JSONField(name = "msg")
    private String msg;//错误信息
    @JSONField(name = "data")
    private String data;//需要的数据

    public  HttpEntity(){

    }
    protected HttpEntity(Parcel in) {
        code = in.readInt();
        msg = in.readString();
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        parcel.writeInt(code);
        parcel.writeString(msg);
        parcel.writeString(data);
    }
}
