package com.siyi.train.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.siyi.train.batch.feign.BusinessFeign;
import com.siyi.train.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.MDC;

import java.util.Date;

/**
 * @ClassName: DailyTrainJob
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 18:08
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@DisallowConcurrentExecution
public class DailyTrainJob implements Job {
    
    private final BusinessFeign businessFeign;

    public DailyTrainJob(BusinessFeign businessFeign) {
        this.businessFeign = businessFeign;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 增加日志流水号
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        log.info("生成15天后的车次数据开始");
        Date date = new Date();
        DateTime dateTime = DateUtil.offsetDay(date, 15);
        Date offsetDate = dateTime.toJdkDate();
        Result<Object> result = this.businessFeign.genDaily(offsetDate);
        log.info("生成15天后的车次数据结束，结果：{}", result);
    }
}