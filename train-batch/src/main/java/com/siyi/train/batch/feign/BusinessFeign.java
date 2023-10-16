package com.siyi.train.batch.feign;

import com.siyi.train.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

/**
 * @ClassName: BusinessFeign
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/14 15:38
 * @Description:
 * @Version 1.0.0
 */
@FeignClient(value = "business", fallback = BusinessFeignFallback.class)
//@FeignClient(name = "business", url = "http://127.0.0.1:8002")
public interface BusinessFeign {

    @GetMapping("/business/admin/daily-train/gen-daily/{date}")
    Result<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
