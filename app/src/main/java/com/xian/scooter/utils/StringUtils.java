package com.xian.scooter.utils;


import android.text.TextUtils;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

/**
 * Created by DELL60 on 2018/3/8.
 */

public class StringUtils {

    /**
     * 获取文件所在的bucket
     *
     * @param str
     * @return
     */
    public static String getBucket(String str) {
        if (str != null) {
            String[] sResult = str.split("_");
            if (sResult.length >= 3) {
                return sResult[1];
            }
            if (str.contains("bit-test")) {
                return "bit-test";
            }
        }
        return "bit-smcm-img";
    }

    public static String makeCardXing(String identity) {
        int j = identity.length() - 1;
        StringBuilder sb = new StringBuilder(identity);
        sb.replace(1, j, "****************");
        return sb.toString();
    }

    /**
     * 是否为空白的，带有去掉空格
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 由6-16位数字或这字母组成
     *
     * @param str
     * @return
     */
    public static boolean isOkPassword(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return str.matches(regex);
    }

    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     *
     * @param value
     * @return
     */
    public static boolean isTelNumber(String value) {
        Pattern p = Pattern.compile("[1][3456789]\\d{9}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 由只能输入汉字,英文，数字
     *
     * @param str
     * @return
     */
    public static boolean isChiEngNum(String str) {
        String regex = "^[^a-zA-Z0-9\\u4E00-\\u9FA5_]";
        return str.matches(regex);
    }

    /**
     * 判断多个字符串是否有空值
     *
     * @param strings
     * @return
     * @author baishuang.wu
     * @date 2014-12-3 上午11:25:39
     */
    public static boolean isNotNull(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            if (null == strings[i] || "".equals(strings[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(Object object) {
        if (null != object && !"".equals(object)) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(List<?> t) {
        if (null != t && t.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Map<?, ?> t) {
        if (null != t && t.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object[] objects) {
        if (null != objects && objects.length > 0) {
            return true;
        }
        return false;
    }




    /**
     * 首字母转换为大写
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * 对字符串进行反序
     */
    public static String reverseStr(String str) {
        StringBuilder result = new StringBuilder(str).reverse();
        return result.toString();
    }

    /**
     * 是否内容全为数字
     *
     * @param text
     * @return
     */
    public static boolean isWholeDigit(String text) {
        boolean isWholeDigit = true;
        for (int i = 0; i < text.length(); i++) {
            boolean isDigit = Character.isDigit(text.charAt(i));
            if (!isDigit) {
                isWholeDigit = false;
                break;
            }
        }
        return isWholeDigit;
    }

    // 顺序表
    static String orderStr = "";

    static {
        for (int i = 33; i < 127; i++) {
            orderStr += Character.toChars(i)[0];
        }
    }

    /**
     * 判断是否有顺序
     *
     * @param str 传入的字符串
     * @return
     */
    public static boolean isOrder(String str) {
        if (!str.matches("((\\d)|([a-z])|([A-Z]))+")) {
            return false;
        }
        return orderStr.contains(str);
    }

    /**
     * 去掉空格
     *
     * @param str 如:"138 0013 8000"为：13800138000
     * @return 返回:"13800138000"
     */
    public static String removeBlank(String str) {
        String tempStr = str.replace(" ", "");
        return tempStr;
    }


    /**
     * 加密身份证中间后四位（只用做加密，不可逆）
     *
     * @param encryptIds
     * @return
     */
    public static String getEncrypt4NumIds(String encryptIds) {
        char[] returnEncryptyIds = encryptIds.toCharArray();
        int startIndex = 10, endIndex = 14;
        if (encryptIds.length() > endIndex) {
            for (int i = startIndex; i < endIndex; i++) {
                returnEncryptyIds[i] = '*';
            }
        }
        return new String(returnEncryptyIds);
    }

    /**
     * 把email的中间替换成*，保留用户名前两位和最后一位
     *
     * @param email 传入的email:"abcdef@berchina.com"
     * @return 返回"ab***f@berchina.com"
     */
    public static String replaceEmailMiddle2Asterisk(String email) {
        char[] emailCharArray = email.toCharArray();
        int place = email.indexOf("@");

        // @前面的邮箱用户名大于2个才去加上*号，否则不加
        if (place > 2) {
            int startIndex = 2, endIndex = place - 1;
            for (int i = startIndex; i < endIndex; i++) {
                emailCharArray[i] = '*';
            }
        }
        return new String(emailCharArray);
    }

    /**
     * 把身份证号的中间替换成*
     *
     * @param idNumber 传入的身份证号:"441702198808084321"
     * @return 返回"4417****4321"
     */
    public static String replaceIdNumberMiddle2Asterisk(String idNumber) {
        char[] idNumberCharArray = idNumber.toCharArray();
        int startIndex = 4, endIndex = idNumberCharArray.length - 4;
        for (int i = startIndex; i < endIndex; i++) {
            idNumberCharArray[i] = '*';
        }
        return new String(idNumberCharArray);
    }

    /**
     * 把手机号的中间替换成*
     *
     * @param phoneNumber 传入的手机号:"18664548654"
     * @return 返回"186****8654"
     */
    public static String replacePhoneMiddle2Asterisk(String phoneNumber) {
        char[] phoneCharArray = phoneNumber.toCharArray();
        int startIndex = 3, endIndex = startIndex + 4;
        for (int i = startIndex; i < endIndex; i++) {
            phoneCharArray[i] = '*';
        }
        return new String(phoneCharArray);
    }

    /**
     *  把手机号13250283032换成 132 5028 3803的格式输出
     *
     */
    public  static String phoneForm(String phone){

        if (!TextUtils.isEmpty(phone)){
            if (phone.length() == 11) {

                String strs = phone;
                String strs1 = strs.substring(0, 3);
                String strs2 = strs.substring(3, 7);
                String strs3 = strs.substring(7, 11);
                strs = strs1 + " " + strs2 + " " + strs3;

               return strs;
            } else {
                return  phone;
            }
        }
        return phone+"";
    }


    /**
     * 判断字符是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    /**
     * 是否都是中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseString(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (!isChineseChar(String.valueOf(aChar))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否只是英文或者数字
     */
    public static boolean isEngLishOrDigest(String str) {
        char[] chars = str.toCharArray();
        Pattern p = Pattern.compile("[0-9a-zA-Z]*");
        for (int i = 0; i < chars.length; i++) {
            String s = String.valueOf(chars[i]);
            Matcher matcher = p.matcher(s);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断用户昵称格式
     */

    public static boolean isValidateNickName(String nickName) {
        String validateStr = "^[\\w\\-－＿[0-9]\\u4e00-\\u9fa5\\uFF21-\\uFF3A\\uFF41-\\uFF5A]{4,20}$";
        return nickName.matches(validateStr);
    }

    /**
     * 手机号正则表达式
     **/
    public final static String MOBLIE_PHONE_PATTERN = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|6|7|8]))\\d{8}$";

    /**
     * 通过正则验证是否是合法手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobile(String phoneNumber) {
        Pattern p = Pattern.compile("[1][3456789]\\d{9}");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 通过正则验证导游的
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isGuiderNumber(String phoneNumber) {
        Pattern p = Pattern.compile("[A-Za-z]-\\d{1,4}-\\d{6,12}");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }


    /**
     * 是否是包含中文
     *
     * @param name
     * @return
     */
    public static boolean isContainChinese(String name) {
        Pattern p = Pattern.compile("[\\\\u4e00-\\\\u9fa5]+");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否是正确的身份证
     *
     * @return
     */
    public static boolean isRightIdCard(String idCard) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        Matcher m = p.matcher(idCard);
        return m.matches();
    }


    /**
     * 判断字符串的编码格式
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * 切除字符串的九个字 ...
     */
    public static String splitIndex(String text, int textIndex) {
        if (isBlank(text)) {
            return "";
        } else if (text.length() >= textIndex) {
            return text.substring(0, textIndex - 1) + "...";
        } else {
            return text;
        }

    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     *
     */
    static char codePoint;
    public static boolean containsEmoji(String source) {

         int len = source.length();
         for (int i = 0; i < len; i++) {
             codePoint = source.charAt(i);
        }
        if (!isEmojiCharacter(codePoint)) {
             //该字符是Emoji表情
             return true;
         }

     return false;
}
     /**
     * 判断是否是Emoji
                *
     * @param codePoint 比较的单个字符
     * @return
     */ private static boolean isEmojiCharacter(char codePoint) {
         return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); }




}
