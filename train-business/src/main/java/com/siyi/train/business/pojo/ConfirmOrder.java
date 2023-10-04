package com.siyi.train.business.pojo;

import java.util.Date;

public class ConfirmOrder {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.member_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Long memberId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.date
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Date date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.train_code
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private String trainCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.start
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private String start;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.end
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private String end;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.daily_train_ticket_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Long dailyTrainTicketId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.status
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.create_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.update_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column confirm_order.tickets
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    private String tickets;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.id
     *
     * @return the value of confirm_order.id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.id
     *
     * @param id the value for confirm_order.id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.member_id
     *
     * @return the value of confirm_order.member_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.member_id
     *
     * @param memberId the value for confirm_order.member_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.date
     *
     * @return the value of confirm_order.date
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.date
     *
     * @param date the value for confirm_order.date
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.train_code
     *
     * @return the value of confirm_order.train_code
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public String getTrainCode() {
        return trainCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.train_code
     *
     * @param trainCode the value for confirm_order.train_code
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.start
     *
     * @return the value of confirm_order.start
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public String getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.start
     *
     * @param start the value for confirm_order.start
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.end
     *
     * @return the value of confirm_order.end
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public String getEnd() {
        return end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.end
     *
     * @param end the value for confirm_order.end
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.daily_train_ticket_id
     *
     * @return the value of confirm_order.daily_train_ticket_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Long getDailyTrainTicketId() {
        return dailyTrainTicketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.daily_train_ticket_id
     *
     * @param dailyTrainTicketId the value for confirm_order.daily_train_ticket_id
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setDailyTrainTicketId(Long dailyTrainTicketId) {
        this.dailyTrainTicketId = dailyTrainTicketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.status
     *
     * @return the value of confirm_order.status
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.status
     *
     * @param status the value for confirm_order.status
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.create_time
     *
     * @return the value of confirm_order.create_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.create_time
     *
     * @param createTime the value for confirm_order.create_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.update_time
     *
     * @return the value of confirm_order.update_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.update_time
     *
     * @param updateTime the value for confirm_order.update_time
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column confirm_order.tickets
     *
     * @return the value of confirm_order.tickets
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public String getTickets() {
        return tickets;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column confirm_order.tickets
     *
     * @param tickets the value for confirm_order.tickets
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table confirm_order
     *
     * @mbg.generated Tue Sep 19 17:41:13 CST 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", date=").append(date);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", dailyTrainTicketId=").append(dailyTrainTicketId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", tickets=").append(tickets);
        sb.append("]");
        return sb.toString();
    }
}