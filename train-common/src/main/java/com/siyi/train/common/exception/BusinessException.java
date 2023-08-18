package com.siyi.train.common.exception;

import com.siyi.train.common.constant.ResultCode;
import lombok.Getter;

/**
 * @ClassName: BusinessException
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 21:44
 * @Description:
 * @Version 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -9118505261060853881L;

    private final ResultCode resultCode;

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BusinessException(String msg) {
        this.resultCode = ResultCode.FAIL;
        this.resultCode.setMessage(msg);
    }
}
