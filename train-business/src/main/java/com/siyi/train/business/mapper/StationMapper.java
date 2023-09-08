package com.siyi.train.business.mapper;

import com.siyi.train.business.pojo.Station;
import com.siyi.train.business.pojo.StationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    long countByExample(StationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int deleteByExample(StationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int insert(Station record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int insertSelective(Station record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    List<Station> selectByExample(StationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    Station selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int updateByExampleSelective(@Param("record") Station record, @Param("example") StationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int updateByExample(@Param("record") Station record, @Param("example") StationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int updateByPrimaryKeySelective(Station record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station
     *
     * @mbg.generated Fri Sep 08 16:18:09 CST 2023
     */
    int updateByPrimaryKey(Station record);
}