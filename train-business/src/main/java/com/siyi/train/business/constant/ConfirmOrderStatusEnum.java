package com.siyi.train.business.constant;

import lombok.Getter;

/**
 * @ClassName: ConfirmOrderStatusEnum
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/19 17:43
 * @Description:
 * @Version 1.0.0
 */
@Getter
public enum ConfirmOrderStatusEnum {

    INIT("I", "初始"),
    PENDING("P", "处理中"),
    SUCCESS("S", "成功"),
    FAILURE("F", "失败"),
    EMPTY("E", "无票"),
    CANCEL("C", "取消");

    private final String code;

    private final String desc;

    ConfirmOrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override    public String toString() {
        return "ConfirmOrderStatusEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}

