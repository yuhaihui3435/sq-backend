package com.neuray.wp.ScheduledTasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName Application
 * @Description TODO
 * @Author zzq
 * @Date 2019/7/15 9:54
 * @Version 1.0
 **/
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
