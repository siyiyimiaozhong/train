package com.siyi.train.business.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class DailyTrainTicket {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.id
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.date
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Date date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.train_code
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private String trainCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.start
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private String start;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.start_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private String startPinyin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.start_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Date startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.start_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer startIndex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.end
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private String end;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.end_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private String endPinyin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.end_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Date endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.end_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer endIndex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.ydz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer ydz;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.ydz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private BigDecimal ydzPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.edz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer edz;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.edz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private BigDecimal edzPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.rw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer rw;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.rw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private BigDecimal rwPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.yw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Integer yw;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.yw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private BigDecimal ywPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.create_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column daily_train_ticket.update_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.id
     *
     * @return the value of daily_train_ticket.id
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.id
     *
     * @param id the value for daily_train_ticket.id
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.date
     *
     * @return the value of daily_train_ticket.date
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.date
     *
     * @param date the value for daily_train_ticket.date
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.train_code
     *
     * @return the value of daily_train_ticket.train_code
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public String getTrainCode() {
        return trainCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.train_code
     *
     * @param trainCode the value for daily_train_ticket.train_code
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.start
     *
     * @return the value of daily_train_ticket.start
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public String getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.start
     *
     * @param start the value for daily_train_ticket.start
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.start_pinyin
     *
     * @return the value of daily_train_ticket.start_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public String getStartPinyin() {
        return startPinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.start_pinyin
     *
     * @param startPinyin the value for daily_train_ticket.start_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setStartPinyin(String startPinyin) {
        this.startPinyin = startPinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.start_time
     *
     * @return the value of daily_train_ticket.start_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.start_time
     *
     * @param startTime the value for daily_train_ticket.start_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.start_index
     *
     * @return the value of daily_train_ticket.start_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.start_index
     *
     * @param startIndex the value for daily_train_ticket.start_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.end
     *
     * @return the value of daily_train_ticket.end
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public String getEnd() {
        return end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.end
     *
     * @param end the value for daily_train_ticket.end
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.end_pinyin
     *
     * @return the value of daily_train_ticket.end_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public String getEndPinyin() {
        return endPinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.end_pinyin
     *
     * @param endPinyin the value for daily_train_ticket.end_pinyin
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEndPinyin(String endPinyin) {
        this.endPinyin = endPinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.end_time
     *
     * @return the value of daily_train_ticket.end_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.end_time
     *
     * @param endTime the value for daily_train_ticket.end_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.end_index
     *
     * @return the value of daily_train_ticket.end_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.end_index
     *
     * @param endIndex the value for daily_train_ticket.end_index
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.ydz
     *
     * @return the value of daily_train_ticket.ydz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getYdz() {
        return ydz;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.ydz
     *
     * @param ydz the value for daily_train_ticket.ydz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setYdz(Integer ydz) {
        this.ydz = ydz;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.ydz_price
     *
     * @return the value of daily_train_ticket.ydz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public BigDecimal getYdzPrice() {
        return ydzPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.ydz_price
     *
     * @param ydzPrice the value for daily_train_ticket.ydz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setYdzPrice(BigDecimal ydzPrice) {
        this.ydzPrice = ydzPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.edz
     *
     * @return the value of daily_train_ticket.edz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getEdz() {
        return edz;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.edz
     *
     * @param edz the value for daily_train_ticket.edz
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEdz(Integer edz) {
        this.edz = edz;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.edz_price
     *
     * @return the value of daily_train_ticket.edz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public BigDecimal getEdzPrice() {
        return edzPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.edz_price
     *
     * @param edzPrice the value for daily_train_ticket.edz_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setEdzPrice(BigDecimal edzPrice) {
        this.edzPrice = edzPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.rw
     *
     * @return the value of daily_train_ticket.rw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getRw() {
        return rw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.rw
     *
     * @param rw the value for daily_train_ticket.rw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setRw(Integer rw) {
        this.rw = rw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.rw_price
     *
     * @return the value of daily_train_ticket.rw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public BigDecimal getRwPrice() {
        return rwPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.rw_price
     *
     * @param rwPrice the value for daily_train_ticket.rw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setRwPrice(BigDecimal rwPrice) {
        this.rwPrice = rwPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.yw
     *
     * @return the value of daily_train_ticket.yw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Integer getYw() {
        return yw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.yw
     *
     * @param yw the value for daily_train_ticket.yw
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setYw(Integer yw) {
        this.yw = yw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.yw_price
     *
     * @return the value of daily_train_ticket.yw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public BigDecimal getYwPrice() {
        return ywPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.yw_price
     *
     * @param ywPrice the value for daily_train_ticket.yw_price
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setYwPrice(BigDecimal ywPrice) {
        this.ywPrice = ywPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.create_time
     *
     * @return the value of daily_train_ticket.create_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.create_time
     *
     * @param createTime the value for daily_train_ticket.create_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column daily_train_ticket.update_time
     *
     * @return the value of daily_train_ticket.update_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column daily_train_ticket.update_time
     *
     * @param updateTime the value for daily_train_ticket.update_time
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_train_ticket
     *
     * @mbg.generated Fri Sep 15 17:31:07 CST 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", start=").append(start);
        sb.append(", startPinyin=").append(startPinyin);
        sb.append(", startTime=").append(startTime);
        sb.append(", startIndex=").append(startIndex);
        sb.append(", end=").append(end);
        sb.append(", endPinyin=").append(endPinyin);
        sb.append(", endTime=").append(endTime);
        sb.append(", endIndex=").append(endIndex);
        sb.append(", ydz=").append(ydz);
        sb.append(", ydzPrice=").append(ydzPrice);
        sb.append(", edz=").append(edz);
        sb.append(", edzPrice=").append(edzPrice);
        sb.append(", rw=").append(rw);
        sb.append(", rwPrice=").append(rwPrice);
        sb.append(", yw=").append(yw);
        sb.append(", ywPrice=").append(ywPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}