package com.siyi.train.member.dto;

import com.siyi.train.common.dto.PageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TicketQueryDto extends PageDto implements Serializable {
    private Long memberId;
}