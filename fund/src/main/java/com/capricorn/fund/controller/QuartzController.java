package com.capricorn.fund.controller;


import com.capricorn.fund.entity.PageRequest;
import com.capricorn.fund.job.annotation.SyncJob;
import com.capricorn.fund.job.manager.JobManager;
import com.capricorn.fund.service.impl.FundServiceImpl;
import com.github.pagehelper.PageInfo;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/quartz")
public class QuartzController {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private JobManager jobManager;
    @Resource
    private FundServiceImpl iFundService;

    @PostMapping("modifyQuartz")
    public String modifyQuartz(@RequestParam(value = "time" ,defaultValue = "30") String time){
        String corn = "0/30 * * * * ? *".replace("30",time);
        Map<String, Object> syncJobMap = this.applicationContext.getBeansWithAnnotation(SyncJob.class);
        for (Object syncJob : syncJobMap.values()){
            if (syncJob instanceof Job){
                SyncJob annotation = syncJob.getClass().getAnnotation(SyncJob.class);
                try {
                    jobManager.rescheduleJob(annotation.triggerName(), annotation.triggerGroup(),corn);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        return "更新表达式为："+corn;
    }

    @GetMapping("findAllFund")
    public PageInfo<Object> findAllFund(PageRequest pageRequest){
        PageInfo<Object> pageInfo =iFundService.findAllFund(pageRequest.getPageSize(),pageRequest.getPageNum());
        return pageInfo;
    }
}
