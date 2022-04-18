package com.bruce.self.config;

/**
 * 利用ThreadLocal封装的保存数据源上线的上下文context
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceContextHolder
 * @date 2022-04-12
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    /**
     * 赋值
     *
     * @param datasourceType
     */
    public static void set(String datasourceType) {
        context.set(datasourceType);
    }

    /**
     * 获取值
     * @return
     */
    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
