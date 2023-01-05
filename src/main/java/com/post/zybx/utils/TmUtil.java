package com.post.zybx.utils;

/**
 * create by Luler on 2023/1/5 11:48
 *
 * @description
 */
public class TmUtil {


    /**
     * 名字脱敏
     *
     * @param name
     * @return
     */
    public static String nameEncrypt(String name) {
        if (StringUtil.isNotEmpty(name)) {
            if (name.length() >= 4) {
                name = name.substring(0, name.length() - 2) + "**";
            } else {
                name = name.substring(0, name.length() - 1) + "*";
            }
        }
        return name;
    }


    /**
     * 手机号码脱敏，只显示前三后四
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtil.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

    }

    //身份证脱敏，显示前三后四
    public static String idCardEncrypt(String id) {
        if (StringUtil.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        if (id.length() == 18) {
            return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
        }else {
            return idPassport(id);
        }
    }


    //护照和出生证脱敏，脱敏后4位
    public static String idPassport(String id) {
        if (StringUtil.isEmpty(id) || (id.length() < 8)) {
            return id;
        }

        return id.substring(id.length()-4);

    }
}
