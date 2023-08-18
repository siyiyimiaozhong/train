package com.siyi.train.member.mapper;

import com.siyi.train.member.pojo.Passenger;
import com.siyi.train.member.pojo.PassengerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassengerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    long countByExample(PassengerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int deleteByExample(PassengerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int insert(Passenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int insertSelective(Passenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    List<Passenger> selectByExample(PassengerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    Passenger selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int updateByExampleSelective(@Param("record") Passenger record, @Param("example") PassengerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int updateByExample(@Param("record") Passenger record, @Param("example") PassengerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int updateByPrimaryKeySelective(Passenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passenger
     *
     * @mbg.generated Fri Aug 18 17:36:00 CST 2023
     */
    int updateByPrimaryKey(Passenger record);
}