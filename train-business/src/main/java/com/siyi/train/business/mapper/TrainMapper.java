package com.siyi.train.business.mapper;

import com.siyi.train.business.pojo.Train;
import com.siyi.train.business.pojo.TrainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrainMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    long countByExample(TrainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int deleteByExample(TrainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int insert(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int insertSelective(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    List<Train> selectByExample(TrainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    Train selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int updateByExampleSelective(@Param("record") Train record, @Param("example") TrainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int updateByExample(@Param("record") Train record, @Param("example") TrainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int updateByPrimaryKeySelective(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbg.generated Fri Sep 08 17:47:48 CST 2023
     */
    int updateByPrimaryKey(Train record);
}