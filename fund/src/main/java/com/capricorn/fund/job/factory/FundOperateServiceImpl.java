package com.capricorn.fund.job.factory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FundOperateServiceImpl implements IOperateService{
    @Override
    public int operateForAdd() {
        log.info("调用了operateForAdd");
        return 0;
    }

    @Override
    public int operateForDelete() {
        log.info("调用了operateForDelete");
        return 0;
    }

    @Override
    public int operateForUpdate() {
        log.info("调用了operateForUpdate");
        return 0;
    }

    @Override
    public void operateForQuery() {
        log.info("调用了operateForQuery");

    }
}
