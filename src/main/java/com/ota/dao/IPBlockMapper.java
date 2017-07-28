package com.ota.dao;

import java.util.List;

import com.ota.domain.IPBlock;

public interface IPBlockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    int insert(IPBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    int insertSelective(IPBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    IPBlock selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IPBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ipblock
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(IPBlock record);

	List<IPBlock> selectAllIPBlock();

	void closeIPBlockByID(Integer id);

	void closeIPBlock(IPBlock ipBlock);
}