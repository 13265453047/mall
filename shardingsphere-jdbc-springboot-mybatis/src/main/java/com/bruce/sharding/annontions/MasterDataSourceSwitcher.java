package com.bruce.sharding.annontions;

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
public @interface MasterDataSourceSwitcher {

}
