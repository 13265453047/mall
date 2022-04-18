package com.bruce.self.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceEnum
 * @date 2022-04-12
 */
@Getter
@AllArgsConstructor
public enum DataSourceEnum {

    MASTER("master"),
    SLAVE_0("slave0"),
    SLAVE_1("slave1");

    private String dataSourceName;

}
