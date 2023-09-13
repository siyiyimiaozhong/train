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
    MEMBER_MOBILE_NOT_EXIST(false, 20009, "请先获取短信验证码"),
    MEMBER_MOBILE_CODE_ERROR(false, 20010, "短信验证码错误"),

    /**
     * 车站相关错误
     */
    BUSINESS_STATION_NAME_UNIQUE_ERROR(false,  30001, "车站已存在"),
    BUSINESS_TRAIN_CODE_UNIQUE_ERROR(false,  30002, "车次编号已存在"),
    BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR(false,  30003, "同车次站序已存在"),
    BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR(false,  30004, "同车次站名已存在"),
    BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR(false,  30005, "同车次厢号已存在"),

    /**
     * 任务相关错误
     */
    JOB_TASK_LAUNCH_FAILED(false, 40001, "任务启动失败"),
    JOB_CREATE_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40002, "创建定时任务失败:调度异常"),
    JOB_CREATE_TASK_FAILED_TASK_CLASS_NOT_EXIST(false, 40003, "创建定时任务失败：任务类不存在"),
    JOB_PAUSE_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40004, "暂停定时任务失败:调度异常"),
    JOB_RESUME_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40005, "重启定时任务失败:调度异常"),
    JOB_RENEW_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40006, "更新定时任务失败:调度异常"),
    JOB_DELETE_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40007, "删除定时任务失败:调度异常"),
    JOB_SELECT_TASK_FAILED_SCHEDULING_EXCEPTION(false, 40008, "查看定时任务失败:调度异常"),
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