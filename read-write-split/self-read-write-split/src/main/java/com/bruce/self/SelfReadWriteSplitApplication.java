package com.bruce.self;

import com.bruce.self.config.properties.DatasourceMaster;
import com.bruce.self.config.properties.DatasourceSlaver0;
import com.bruce.self.config.properties.DatasourceSlaver1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {DatasourceMaster.class, DatasourceSlaver0.class, DatasourceSlaver1.class})
@SpringBootApplication
public class SelfReadWriteSplitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfReadWriteSplitApplication.class, args);
    }

}
