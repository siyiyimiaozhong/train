package com.siyi.train.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ConfirmOrderMQDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/24 14:43
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderMQDto implements Serializable {
    /**
     * 日志流程号，用于同转异时，用同一个流水号
     */
    private String logId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 车次编号
     */
    private String trainCode;
}
