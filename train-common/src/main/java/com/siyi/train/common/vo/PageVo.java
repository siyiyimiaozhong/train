package com.siyi.train.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageVo
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/31 17:34
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> implements Serializable {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页的列表
     */
    private List<T> list;
}
