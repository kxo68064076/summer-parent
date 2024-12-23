package com.capricorn.fund.job.manager;

import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Component
public class JobManager {

    @Resource
    private Scheduler scheduler;

    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron, String desc,
                       Class<? extends Job> newJob, JobListener... listeners) throws SchedulerException {
        if (scheduler.getJobDetail(JobKey.jobKey(jName,jGroup))==null){
            JobDetail jobDetail = JobBuilder.newJob(newJob)
                    .withIdentity(jName, jGroup)
                    .withDescription(desc)
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tName, tGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            if (listeners!=null){
                Arrays.stream(listeners).forEach(listener->{
                    try {
                        scheduler.getListenerManager().addJobListener(listener);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                });
            }

            scheduler.start();
            scheduler.scheduleJob(jobDetail,trigger);
        }
    }

    public void rescheduleJob(String tName,String tGroup,String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(tName, tGroup);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.rescheduleJob(triggerKey,trigger);
    }

}
