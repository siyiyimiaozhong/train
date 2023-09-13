package com.siyi.train.batch.controller;

import com.siyi.train.batch.dto.CronJobDto;
import com.siyi.train.batch.service.JobService;
import com.siyi.train.batch.vo.CronJobVo;
import com.siyi.train.common.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: JobController
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:45
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/admin/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "/run")
    public Result<Object> run(@RequestBody CronJobDto dto) {
        this.jobService.run(dto);
        return Result.success();
    }

    @RequestMapping(value = "/add")
    public Result<Object> add(@RequestBody CronJobDto dto) {
        this.jobService.addJob(dto);
        return Result.success();
    }

    @RequestMapping(value = "/pause")
    public Result<Object> pause(@RequestBody CronJobDto dto) {
        this.jobService.pause(dto);
        return Result.success();
    }

    @RequestMapping(value = "/resume")
    public Result<Object> resume(@RequestBody CronJobDto dto) {
        this.jobService.resume(dto);
        return Result.success();
    }

    @RequestMapping(value = "/reschedule")
    public Result<Object> reschedule(@RequestBody CronJobDto dto) {
        this.jobService.reschedule(dto);
        return Result.success();
    }

    @RequestMapping(value = "/delete")
    public Result<Object> delete(@RequestBody CronJobDto dto) {
        this.jobService.delete(dto);
        return Result.success();
    }

    @RequestMapping(value="/query")
    public Result<List<CronJobVo>> query() {
        List<CronJobVo> list = this.jobService.query();
        return Result.success(list);
    }
}
