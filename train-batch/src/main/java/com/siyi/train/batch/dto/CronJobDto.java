package com.siyi.train.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CronJobDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/13 2:57
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CronJobDto {
    private String group;

    private String name;

    private String description;

    private String cronExpression;
}
