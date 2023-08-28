package com.siyi.train.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: MemberLoginVo
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/22 16:49
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginVo implements Serializable {
    private Long id;
    private String mobile;

    private String token;
}
