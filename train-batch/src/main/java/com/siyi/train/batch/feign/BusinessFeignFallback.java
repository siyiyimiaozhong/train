package com.siyi.train.batch.feign;

import com.siyi.train.common.vo.Result;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: BusinessFeignFallback
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/14 23:37
 * @Description:
 * @Version 1.0.0
 */
@Component
public class BusinessFeignFallback implements BusinessFeign {

    @Override
    public Result<Object> genDaily(Date date) {
        return null;
    }
}
