package com.siyi.train.common.vo;

import com.siyi.train.common.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Result
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 21:15
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public Result(ResultCode code) {
        this.success = code.isSuccess();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public Result(ResultCode code, T data) {
        this.success = code.isSuccess();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultCode.SERVER_ERROR);
    }

    public static <T> Result<T> error(ResultCode code) {
        return new Result<>(code);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ResultCode.SERVER_ERROR.getCode(), message, false);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAIL);
    }
}
