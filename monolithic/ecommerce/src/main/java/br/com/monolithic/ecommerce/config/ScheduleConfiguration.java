package br.com.monolithic.ecommerce.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(name = "tasksExecutor")
    public Executor taskExecutor() {
        ThreadFactory namedThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("tasksExecutor-%d").setPriority(Thread.MIN_PRIORITY).build();
        return Executors.newScheduledThreadPool(10, namedThreadFactory);
    }
}
