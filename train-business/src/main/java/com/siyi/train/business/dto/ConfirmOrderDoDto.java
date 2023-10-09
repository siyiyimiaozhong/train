package com.siyi.train.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderDoDto implements Serializable {
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
    * 出发站
    */
    @NotBlank(message = "【出发站】不能为空")
    private String start;
    /**
    * 到达站
    */
    @NotBlank(message = "【到达站】不能为空")
    private String end;
    /**
    * 余票ID
    */
    @NotNull(message = "【余票ID】不能为空")
    private Long dailyTrainTicketId;
    /**
    * 车票
    */
    @NotNull(message = "【车票】不能为空")
    private List<ConfirmOrderTicketDto> tickets;
}