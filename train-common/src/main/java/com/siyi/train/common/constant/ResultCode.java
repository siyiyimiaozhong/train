package com.siyi.train.common.constant;

/**
 * @ClassName: ResultCode
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 21:20
 * @Description:
 * @Version 1.0.0
 */
public enum ResultCode {

    /**
     * 通用成功
     */
    SUCCESS(true, 10000, "操作成功！"),
    /**
     * 通用错误
     */
    FAIL(false, 10001, "操作失败"),
    /**
     * 参数验证失败
     */
    PARAMETER_VALIDATION_FAILED(false, 10002, "参数验证失败"),
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),


    /**
     * 用户操作返回码
     */
    UNAUTHENTICATED(false, 20001, "您还未登录"),
    WRONG_USERNAME_OR_PASSWORD(false, 20002, "用户名或者密码错误"),
    LOGIN_FAILED(false, 20003, "登录失败"),
    ROLE_EXCEPTION(false, 20004, "当前用户没有对应角色权限"),
    PERMISSION_EXCEPTION(false, 20005, "当前用户没有对应权限"),
    ACCOUNT_DISABLED(false, 20006, "该账号已被禁用"),
    PASSWORD_ERROR(false, 20007, "密码错误"),
    MEMBER_MOBILE_EXIST(false, 20008, "手机号已注册"),
    ;

    /**
     * 操作是否成功
     */
    private boolean success;
    /**
     * 操作代码
     */
    private int code;
    /**
     * 提示信息
     */
    private String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}