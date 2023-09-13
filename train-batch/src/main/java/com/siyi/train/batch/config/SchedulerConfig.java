package com.siyi.train.batch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @ClassName: SchedulerConfig
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:47
 * @Description:
 * @Version 1.0.0
 */
@Configuration
public class SchedulerConfig {

    private final MyJobFactory myJobFactory;

    public SchedulerConfig(MyJobFactory myJobFactory) {
        this.myJobFactory = myJobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(myJobFactory);
        factory.setStartupDelay(2);
        return factory;
    }
}
