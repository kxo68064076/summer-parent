package com.capricorn.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {
    /**
     * 核心线程数
     */
    private static int corePoolSize;

    /**
     * 线程池中允许的最大线程数
     */
    private static int maximumPoolSize ;

    /**
     * 线程空闲超时时间（秒）
     */
    private static final long keepAliveTime = 30;

    /**
     * 任务队列
     */
    private static final int capacity = 100;
    //private final ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(100);

    private static final  TimeUnit unit = TimeUnit.SECONDS;
    /**
     * 站内信、短信-线程池
     *
     * @return
     */
    @Bean()
    public ThreadPoolExecutor myThreadPoolExecutor() {
        corePoolSize = Runtime.getRuntime().availableProcessors();
        maximumPoolSize=corePoolSize*2;
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity));
    }
}
