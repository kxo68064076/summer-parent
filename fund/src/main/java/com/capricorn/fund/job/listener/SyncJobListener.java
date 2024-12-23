package com.capricorn.fund.job.listener;

import com.capricorn.fund.job.annotation.SyncJob;
import com.capricorn.fund.job.manager.JobManager;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class SyncJobListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private JobManager jobManager;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> syncJobMap = this.applicationContext.getBeansWithAnnotation(SyncJob.class);
        for (Object syncJob : syncJobMap.values()){
            if (syncJob instanceof Job){
                SyncJob annotation = syncJob.getClass().getAnnotation(SyncJob.class);
                JobListener jobListener = null;
                if (syncJob instanceof JobListener){
                    jobListener = (JobListener) syncJob;
                }
                try {
                    jobManager.addJob(annotation.jobName(), annotation.jobGroup(), annotation.triggerName(), annotation.triggerGroup(),
                            annotation.corn(), annotation.desc(), (Class<? extends Job>) syncJob.getClass(),jobListener);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
