package com.siyi.train.batch.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName: TestJob
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 15:22
 * @Description:
 * @Version 1.0.0
 */
@DisallowConcurrentExecution
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("TestJob TEST开始");
         try {
             Thread.sleep(3000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
        System.out.println("TestJob TEST结束");
    }
}
