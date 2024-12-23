package com.capricorn.fund.job.factory;

import com.capricorn.common.utils.SpringUtil;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzCustomAdaptableJobFactory extends AdaptableJobFactory {

    private final Object monitor = new Object();

    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance (TriggerFiredBundle bundle) throws Exception {
        Object job = SpringUtil.getBean(bundle.getJobDetail().getJobClass());
        if (job == null) {
            synchronized (monitor) {
                job = SpringUtil.getBean(bundle.getJobDetail().getJobClass());
                if (job == null) {
                    //实例化对象
                    job = super.createJobInstance(bundle);
                    //进行注入，交給spring管理该bean
                    capableBeanFactory.autowireBean(job);
                }
            }
        }
        return job;
    }
}
