package com.siyi.train.business.pojo;

import java.util.Date;

public class Station {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.id
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.name
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.name_pinyin
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private String namePinyin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.name_py
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private String namePy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.create_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station.update_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.id
     *
     * @return the value of station.id
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.id
     *
     * @param id the value for station.id
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.name
     *
     * @return the value of station.name
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.name
     *
     * @param name the value for station.name
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.name_pinyin
     *
     * @return the value of station.name_pinyin
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public String getNamePinyin() {
        return namePinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.name_pinyin
     *
     * @param namePinyin the value for station.name_pinyin
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.name_py
     *
     * @return the value of station.name_py
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public String getNamePy() {
        return namePy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.name_py
     *
     * @param namePy the value for station.name_py
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.create_time
     *
     * @return the value of station.create_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.create_time
     *
     * @param createTime the value for station.create_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station.update_time
     *
     * @return the value of station.update_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station.update_time
     *
     * @param updateTime the value for station.update_time
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", namePinyin=").append(namePinyin);
        sb.append(", namePy=").append(namePy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}