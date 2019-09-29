package com.xian.scooter.utils;

import android.os.Handler;

import java.io.File;
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
     * 多张图片上传
     * @param fileList 图片地址
     * @param pathList 放置网络上返回地址的列表
     * @param handler
     */
    public void updateImage(List<File> fileList, List<String> pathList, Handler handler) {

        for (int i=0;i<fileList.size();i++) {
            int finalI = i;
//            ApiRequest.getInstance().post(HttpURL.UPLOAD_FILE, fileList.get(i), new DefineCallback<FileBean>() {
//                @Override
//                public void onMyResponse(SimpleResponse<FileBean, HttpEntity> response) {
//                    if (response.isSucceed()) {
//                            FileBean fileBean = response.succeed();
//                            if (fileBean != null) {
//                                String path = fileBean.getPath();
//                                pathList.add(path);
//                            }
//                        if (finalI ==fileList.size()-1) {
//                            Message message = handler.obtainMessage();
//                            message.what = 1;
//                            message.obj=pathList;
//                            handler.sendMessage(message);
//                        }
//                    }else {
//                        //每次都可能失败
//                        Message message = handler.obtainMessage();
//                        message.what = 0;
//                        message.obj=pathList;
//                        handler.sendMessage(message);
//                    }
//                }
//            });

        }
    }
}
