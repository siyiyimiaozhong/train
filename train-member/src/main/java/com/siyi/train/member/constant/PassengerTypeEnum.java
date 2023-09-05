package com.siyi.train.member.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: PassengerTypeEnum
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 14:00
 * @Description: 乘客类型
 * @Version 1.0.0
 */
public enum PassengerTypeEnum {

    /**
     * 成人
     */
    ADULT("1", "成人"),
    CHILD("2", "儿童"),
    STUDENT("3", "学生"),
    ;

    private String code;

    private String desc;

    PassengerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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

    public static List<HashMap<String,String>> getEnumList() {
        List<HashMap<String, String>> list = new ArrayList<>();
        for (PassengerTypeEnum anEnum : EnumSet.allOf(PassengerTypeEnum.class)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("code",anEnum.code);
            map.put("desc",anEnum.desc);
            list.add(map);
        }
        return list;
    }
}
