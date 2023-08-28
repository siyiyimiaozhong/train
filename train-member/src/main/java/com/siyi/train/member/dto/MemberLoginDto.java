package com.siyi.train.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: MemberLoginDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/22 16:22
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto implements Serializable {

    @NotBlank(message = "【手机号】不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "【手机号】格式错误")
    private String mobile;

    @NotBlank(message = "【短信验证码】不能为空")
    private String code;
}
