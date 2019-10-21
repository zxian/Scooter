package com.xian.scooter.net;

public class HttpURL {

    //登录注册
    public static String SIGN_IN = "/cust/login";
    public static String REGISTER = "/cust/register";
    public static String SMS = "/sms/message/{phoneNumber}/{source}";
    public static String FILE_UPLOAD = "/file/headImgUpload";
    public static String CUST = "/cust/pwd";

    //门店
    public static String STORE_ADD = "/store/add";
    public static String STORE_LIST = "/store/page/{size}/{current}";
    public static String STORE_BYID = "/store/byId/{storeId}";

    //教练
    public static String COACH_LIST = "/cust/selectCustUserList/{storeId}/{current}/{size}";
    public static String CUST_ADD = "/cust/add";
    public static String CUST_EDIT = "/cust/edit";

    //赛事
    public static String COMPETITION_LIST = "/competition/page/{size}/{current}";
    public static String COMPETITION_DETAILS = "/competition/byId/{id}";
    public static String COMPETITION_SAVE = "/competition/saveOrUpdateCompetition";
    public static String COMPETITION_SET_LIST = "/competitionSet/list/{competitionId}";
    public static String COMPETITION_SET_SAVE = "/competitionSet/saveOrUpdateCompetitionSet";
    public static String COMPETITION_JOIN = "/competitionJoin/page/{size}/{current}";
    public static String COMPETITION_JOIN_BYID= "/competitionJoin/byId/{id}";

    //课程包
    public static String PACKAGE_PAGE= "/package/page/{size}/{current}";
    public static String PACKAGE_SAVEORUPDATE= "/package/saveOrUpdate";


}
