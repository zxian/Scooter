package com.xian.scooter.net;

public class HttpURL {

    //登录注册
    public static String SIGN_IN = "/cust/login";
    public static String REGISTER = "/cust/register";
    public static String SMS = "/sms/message/{phoneNumber}/{source}";
    public static String FILE_UPLOAD = "/file/headImgUpload";

    //门店
    public static String STORE_ADD = "/store/add";
    public static String STORE_LIST = "/store/page/{size}/{current}";

    //赛事
    public static String COMPETITION_LIST = "/competition/page/{size}/{current}";

}
