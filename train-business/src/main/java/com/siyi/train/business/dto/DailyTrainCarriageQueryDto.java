package com.siyi.train.business.dto;

import com.siyi.train.common.dto.PageDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class DailyTrainCarriageQueryDto extends PageDto implements Serializable {
    private String trainCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}