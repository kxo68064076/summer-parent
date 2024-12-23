package com.capricorn.fund.job.handle;

import com.capricorn.common.utils.BecomingRichUtil;
import com.capricorn.fund.job.annotation.SyncJob;
import com.capricorn.fund.mapper.BecomingRichMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@Slf4j
//@SyncJob(corn = "0 0/5 10-15 * * ?",triggerName = "testTrigger",jobName = "testJob")
public class AccessRealTimeDataJob implements Job, JobListener {

//    @Autowired
//    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private BecomingRichMapper becomingRichMapper;
    @Value("${IP}")
    private String IP;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long start = System.currentTimeMillis();
        becomingRichMapper.delAll();
        List<Map<String, Object>> allFundCode = becomingRichMapper.findAllFundCode();
        //单线程
        allFundCode.forEach(fundCode -> {
//            String fund_code = BecomingRichUtil.request(fundCode.get("FUND_CODE").toString(),IP);
            String fund_code = BecomingRichUtil.req(fundCode.get("FUND_CODE").toString(), IP);
            Map<String, Object> map = BecomingRichUtil.regular(fund_code,fundCode.get("IF_BUY").toString());

            becomingRichMapper.handle(map);
            becomingRichMapper.saveHis(map);
        });
        //并行流
//        allFundCode.parallelStream().forEach(fund ->{
//            String fund_code = BecomingRichUtil.request(fund.get("FUND_CODE").toString());
//            Map<String, Object> map = BecomingRichUtil.regular(fund_code);
//            mapper.handle(map);
//        });
        long end = System.currentTimeMillis();
        log.info(String.format("执行一轮%s条数据耗时：%s ms",allFundCode.size(),end-start));

    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }


    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
    }
}
