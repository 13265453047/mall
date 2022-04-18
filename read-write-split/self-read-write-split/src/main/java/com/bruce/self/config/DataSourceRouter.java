package com.bruce.self.config;

import com.bruce.self.enums.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceRouter
 * @date 2022-04-12
 */
@Slf4j
public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final List<String> slaveDataSourceNames = new CopyOnWriteArrayList<String>() {{
        add(DataSourceEnum.SLAVE_0.getDataSourceName());
        add(DataSourceEnum.SLAVE_1.getDataSourceName());
    }};

    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * 最终的determineCurrentLookupKey返回的是从DataSourceContextHolder中拿到的,因此在动态切换数据源的时候注解
     * 应该给DataSourceContextHolder设值
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DataSourceContextHolder.get();
        if (StringUtils.isEmpty(dataSourceName)) {
            dataSourceName = DataSourceEnum.MASTER.getDataSourceName();
        } else if (Objects.equals(DataSourceEnum.SLAVE_0.getDataSourceName(), dataSourceName) ||
                Objects.equals(DataSourceEnum.SLAVE_1.getDataSourceName(), dataSourceName)) {

            count.compareAndSet(slaveDataSourceNames.size(), 0);

            int countNum = count.getAndIncrement();
            int index = Math.abs(countNum) % slaveDataSourceNames.size();
            log.info("计数器号码：{}, 下标：{}", countNum, index);

            dataSourceName = slaveDataSourceNames.get(index);

            log.info("当前数据源：{}", dataSourceName);
        }

        return dataSourceName;
    }

//    @Override
//    protected Object determineCurrentLookupKey() {
//        return DataSourceContextHolder.get();
//
//    }

}
