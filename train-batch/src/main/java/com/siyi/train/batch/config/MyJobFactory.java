package com.siyi.train.batch.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyJobFactory
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:48
 * @Description:
 * @Version 1.0.0
 */
@Component
public class MyJobFactory extends SpringBeanJobFactory {

    private final AutowireCapableBeanFactory beanFactory;

    public MyJobFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 这里覆盖了super的createJobInstance方法，对其创建出来的类再进行autowire。
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
