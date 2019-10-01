package com.xian.scooter.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadPicturesUtils {

    private static UploadPicturesUtils instance;

    private UploadPicturesUtils() {
    }

    public static UploadPicturesUtils getInstance() {
        if (instance == null) {
            synchronized (UploadPicturesUtils.class) {
                if (instance == null) {
                    instance = new UploadPicturesUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 单张图片上传
     *
     * @param file    图片地址
     * @param handler
     */
    public void updateImage(File file, Handler handler) {
        List<File> fileList = new ArrayList<>();
        List<String> pathList = new ArrayList<>();
        fileList.add(file);
        updateImages(fileList, pathList, handler);
    }

    /**
     * 多张图片上传
     *
     * @param fileList 图片地址
     * @param pathList 放置网络上返回地址的列表
     * @param handler
     */
    public void updateImages(List<File> fileList, List<String> pathList, Handler handler) {

        for (int i = 0; i < fileList.size(); i++) {
            int finalI = i;
            ApiRequest.getInstance().post(HttpURL.FILE_UPLOAD, fileList.get(i), new DefineCallback<String>() {
                @Override
                public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                    if (response.isSucceed()) {
                        String path = response.succeed();
                        if (!TextUtils.isEmpty(path)) {
                            pathList.add(path);
                        }
                        if (finalI == fileList.size() - 1) {
                            Message message = handler.obtainMessage();
                            message.what = 1;
                            message.obj = pathList;
                            handler.sendMessage(message);
                        }
                    } else {
                        //每次都可能失败
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        message.obj = pathList;
                        handler.sendMessage(message);
                    }
                }
            });

        }
    }
}
