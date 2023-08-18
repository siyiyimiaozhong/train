package com.siyi.train.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: MemberDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 21:13
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements Serializable {
    @NotBlank(message = "【手机号】不能为空")
    private String mobile;
}
