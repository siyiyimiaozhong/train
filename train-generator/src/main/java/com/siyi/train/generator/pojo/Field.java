package com.siyi.train.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Field
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/5 17:24
 * @Description:
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    /**
     * 字段名：course_id
     */
    private String name;
    /**
     * 字段名小驼峰：courseId
     */
    private String nameHump;
    /**
     * 字段名大驼峰：CourseId
     */
    private String nameBigHump;
    /**
     * 中文名：课程
     */
    private String nameCn;
    /**
     * 字段类型：char(8)
     */
    private String type;
    /**
     * java类型：String
     */
    private String javaType;
    /**
     * 注释：课程|ID
     */
    private String comment;
    /**
     * 是否可为空
     */
    private Boolean nullAble;
    /**
     * 字符串长度
     */
    private Integer length;
    /**
     * 是否是枚举
     */
    private Boolean enums;
    /**
     * 枚举常量 COURSE_LEVEL
     */
    private String enumsConst;
}
