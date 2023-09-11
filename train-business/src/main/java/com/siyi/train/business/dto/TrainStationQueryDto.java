package com.siyi.train.business.dto;

import com.siyi.train.common.dto.PageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TrainStationQueryDto extends PageDto implements Serializable {
    private String trainCode;
}