package com.siyi.train.batch.service.impl;

import com.siyi.train.batch.dto.CronJobDto;
import com.siyi.train.batch.service.JobService;
import com.siyi.train.batch.vo.CronJobVo;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: JobServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:46
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Service
public class JobServiceImpl implements JobService {

    private final SchedulerFactoryBean schedulerFactoryBean;

    public JobServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @Override
    public void run(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        log.info("手动执行任务开始：{}, {}", jobClassName, jobGroupName);
        try {
            schedulerFactoryBean.getScheduler().triggerJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            throw new BusinessException(ResultCode.JOB_TASK_LAUNCH_FAILED);
        }
    }

    @Override
    public void addJob(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        String cronExpression = dto.getCronExpression();
        String description = dto.getDescription();
        log.info("创建定时任务开始：{}，{}，{}，{}", jobClassName, jobGroupName, cronExpression, description);

        try {
            // 通过SchedulerFactory获取一个调度器实例
            Scheduler sched = schedulerFactoryBean.getScheduler();

            // 启动调度器
            sched.start();

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobClassName)).withIdentity(jobClassName, jobGroupName).build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withDescription(description).withSchedule(scheduleBuilder).build();

            sched.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            log.error("创建定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_CREATE_TASK_FAILED_SCHEDULING_EXCEPTION);
        } catch (ClassNotFoundException e) {
            log.error("创建定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_CREATE_TASK_FAILED_TASK_CLASS_NOT_EXIST);
        }
    }

    @Override
    public void pause(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        log.info("暂停定时任务开始：{}，{}", jobClassName, jobGroupName);
        try {
            Scheduler sched = schedulerFactoryBean.getScheduler();
            sched.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_PAUSE_TASK_FAILED_SCHEDULING_EXCEPTION);
        }
    }

    @Override
    public void resume(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        log.info("重启定时任务开始：{}，{}", jobClassName, jobGroupName);
        try {
            Scheduler sched = schedulerFactoryBean.getScheduler();
            sched.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("重启定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_RESUME_TASK_FAILED_SCHEDULING_EXCEPTION);
        }
    }

    @Override
    public void reschedule(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        String cronExpression = dto.getCronExpression();
        String description = dto.getDescription();
        log.info("更新定时任务开始：{}，{}，{}，{}", jobClassName, jobGroupName, cronExpression, description);

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTriggerImpl trigger1 = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
            trigger1.setStartTime(new Date()); // 重新设置开始时间
            CronTrigger trigger = trigger1;

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(description).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("更新定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_RENEW_TASK_FAILED_SCHEDULING_EXCEPTION);
        }
    }

    @Override
    public void delete(CronJobDto dto) {
        String jobClassName = dto.getName();
        String jobGroupName = dto.getGroup();
        log.info("删除定时任务开始：{}，{}", jobClassName, jobGroupName);
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_DELETE_TASK_FAILED_SCHEDULING_EXCEPTION);
        }
    }

    @Override
    public List<CronJobVo> query() {
        log.info("查看所有定时任务开始");
        List<CronJobVo> cronJobVoList = new LinkedList<>();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    CronJobVo cronJobVo = new CronJobVo();
                    cronJobVo.setName(jobKey.getName());
                    cronJobVo.setGroup(jobKey.getGroup());

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    CronTrigger cronTrigger = (CronTrigger) triggers.get(0);
                    cronJobVo.setNextFireTime(cronTrigger.getNextFireTime());
                    cronJobVo.setPreFireTime(cronTrigger.getPreviousFireTime());
                    cronJobVo.setCronExpression(cronTrigger.getCronExpression());
                    cronJobVo.setDescription(cronTrigger.getDescription());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(cronTrigger.getKey());
                    cronJobVo.setState(triggerState.name());

                    cronJobVoList.add(cronJobVo);
                }

            }
        } catch (SchedulerException e) {
            log.error("查看定时任务失败:" + e);
            throw new BusinessException(ResultCode.JOB_SELECT_TASK_FAILED_SCHEDULING_EXCEPTION);
        }
        return cronJobVoList;
    }
}
