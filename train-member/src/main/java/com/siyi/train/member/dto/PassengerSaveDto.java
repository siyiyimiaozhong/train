package com.siyi.train.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: PassengerSaveDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 14:08
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSaveDto implements Serializable {
    private Long id;
    private Long memberId;
    @NotNull(message = "【名字】不能为空")
    private String name;
    @NotBlank(message = "【身份证】不能为空")
    private String idCard;
    @NotBlank(message = "【旅客类型】不能为空")
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
