package com.gloddy.server.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    public static final String EVENT_HANDLER_TASK_EXECUTOR = "EVENT_HANDLER_TASK_EXECUTOR";
    public static final String EVENT_HANDLER_TASK_EXECUTOR_NAME = "EVENT-HANDLER-TASK-EXECUTOR-";

    @Bean(EVENT_HANDLER_TASK_EXECUTOR)
    public Executor EventHandlerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix(EVENT_HANDLER_TASK_EXECUTOR_NAME);
        return executor;
    }
}
