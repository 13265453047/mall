package com.bruce.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件加载工具类
 *
 * @author rcy
 * @version 1.0.0
 * @className: PropertyUtil
 * @date 2021/10/21 15:12
 */
public class PropertyUtil {

    //加载property文件到io流里面
    public static Properties loadProperties(String propertyFile) {
        Properties properties = new Properties();
        try {
            InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            if (is == null) {
                is = PropertyUtil.class.getClassLoader().getResourceAsStream("properties/" + propertyFile);
            }
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 根据key值取得对应的value值
     *
     * @param key
     * @return
     */
    public static String getValue(String propertyFile, String key) {
        Properties properties = loadProperties(propertyFile);
        return properties.getProperty(key);
    }

}
