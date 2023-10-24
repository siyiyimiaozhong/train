package com.siyi.train.business.constant;

/**
 * @ClassName: RocketMQTopicEnum
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/24 14:50
 * @Description:
 * @Version 1.0.0
 */
public enum RocketMQTopicEnum {

    CONFIRM_ORDER("CONFIRM_ORDER", "确认订单排队");

    private String code;

    private String desc;

    RocketMQTopicEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "RocketMQTopicEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
