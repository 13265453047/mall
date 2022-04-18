package com.bruce.self.annontions;


import com.bruce.self.enums.DataSourceEnum;

import java.lang.annotation.*;

/**
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceSwitcher
 * @date 2022-04-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSwitcher {
    /**
     * 默认数据源
     *
     * @return
     */
    DataSourceEnum value() default DataSourceEnum.MASTER;

    /**
     * 清除
     *
     * @return
     */
    boolean clear() default true;

}
