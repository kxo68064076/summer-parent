package com.capricorn.fund.job.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SyncJob {

    String corn();

    String triggerGroup() default "DEFAULT_TRIGGER_GROUP";

    String jobGroup() default "DEFAULT_GROUP";

    String triggerName() ;

    String jobName();

    String desc() default "";
}
