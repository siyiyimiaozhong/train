package com.siyi.train.business.feign;

import com.siyi.train.common.dto.MemberTicketDto;
import com.siyi.train.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: MemberFeign
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/29 18:46
 * @Description:
 * @Version 1.0.0
 */

 @FeignClient("member")
//@FeignClient(name = "member", url = "http://127.0.0.1:8001")
public interface MemberFeign {

    @GetMapping("/member/feign/ticket/save")
    Result<Object> save(@RequestBody MemberTicketDto dto);

}