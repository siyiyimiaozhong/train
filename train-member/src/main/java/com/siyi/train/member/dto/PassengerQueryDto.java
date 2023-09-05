package com.siyi.train.member.dto;

import com.siyi.train.common.dto.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: PassengerQueryDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/31 15:49
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerQueryDto extends PageDto implements Serializable {
    private Long memberId;
}
