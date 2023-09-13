package com.siyi.train.batch.service;

import com.siyi.train.batch.dto.CronJobDto;
import com.siyi.train.batch.vo.CronJobVo;

import java.util.List;

/**
 * @ClassName: JobService
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:46
 * @Description:
 * @Version 1.0.0
 */
public interface JobService {
    void run(CronJobDto dto);

    void addJob(CronJobDto dto);

    void pause(CronJobDto dto);

    void resume(CronJobDto dto);

    void reschedule(CronJobDto dto);

    void delete(CronJobDto dto);

    List<CronJobVo> query();
}
