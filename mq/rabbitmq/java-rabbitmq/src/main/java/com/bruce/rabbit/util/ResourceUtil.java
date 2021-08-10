package com.bruce.rabbit.util;

import java.util.ResourceBundle;

/**
 * @author rcy
 * @data 2021-08-08 22:41
 * @description 配置文件读取工具类
 */
public class ResourceUtil {

    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
