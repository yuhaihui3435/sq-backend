package com.neuray.wp.scheduledTasks;

import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ScheduledTasks
 * @Description TODO
 * @Author zzq
 * @Date 2019/7/15 9:51
 * @Version 1.0
 **/
@Component
public class ScheduledTasks {

    @Autowired
    private LessonService lessonService;

//    @Scheduled(cron = "0 0 1 * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    @Scheduled(cron = "*/30 * * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void testCron() {
        System.out.println("次执行方法");
        //查询所有课程
        List<Lesson> lessonList = lessonService.many("lesson.lesson.sample",new Lesson());
//        当前时间
        Date currentDate = new Date();
        for (int i = 0; i < lessonList.size(); i++) {
            Date startDate = lessonList.get(i).getLessonAt();
            Date endDate = lessonList.get(i).getLessonEndAt();
            //小于起始时间，未开始
            if (currentDate.before(startDate)){
                lessonList.get(i).setLessonStatus("00");
            }
            //大于起始时间，小于结束时间，进行中
            if (currentDate.after(startDate)&&currentDate.before(endDate)){
                lessonList.get(i).setLessonStatus("01");
            }
            //大于结束时间，已结课
            if (currentDate.after(endDate)){
                lessonList.get(i).setLessonStatus("02");
            }
            lessonService.update(lessonList.get(i));
        }
    }

}
