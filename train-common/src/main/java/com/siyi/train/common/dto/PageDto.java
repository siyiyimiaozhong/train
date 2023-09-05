package com.siyi.train.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: PageDto
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/31 16:23
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto implements Serializable {
    @NotNull(message = "【页码】不能为空")
    private Integer page;

    @NotNull(message = "【每页条数】不能为空")
    @Max(value = 100, message = "【每页条数】不能超过100")
    private Integer size;
}
