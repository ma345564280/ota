package com.ota.dao;

import com.ota.domain.AppstoreMapping;

public interface AppstoreMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    int insert(AppstoreMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    int insertSelective(AppstoreMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    AppstoreMapping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppstoreMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appstoremapping
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AppstoreMapping record);

	int selectlastid();
}