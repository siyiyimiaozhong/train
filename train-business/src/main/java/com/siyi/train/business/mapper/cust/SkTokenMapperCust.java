package com.siyi.train.business.mapper.cust;

import java.util.Date;

/**
 * @ClassName: SkTokenMapperCust
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/16 18:19
 * @Description:
 * @Version 1.0.0
 */
public interface SkTokenMapperCust {

    int decrease(Date date, String trainCode, int decreaseCount);
}
