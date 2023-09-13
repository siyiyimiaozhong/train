package com.siyi.train.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyTrainCarriageSaveDto implements Serializable {

    /**
    * id
    */
    private Long id;
    /**
    * 日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @NotNull(message = "【日期】不能为空")
    private Date date;
    /**
    * 车次编号
    */
    @NotBlank(message = "【车次编号】不能为空")
    private String trainCode;
    /**
    * 箱序
    */
    @NotNull(message = "【箱序】不能为空")
    private Integer index;
    /**
    * 座位类型|枚举[SeatTypeEnum]
    */
    @NotBlank(message = "【座位类型】不能为空")
    private String seatType;
    /**
    * 排数
    */
    @NotNull(message = "【排数】不能为空")
    private Integer rowCount;
    /**
    * 新增时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
    * 修改时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
