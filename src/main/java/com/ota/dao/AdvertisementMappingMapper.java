package com.ota.dao;

import com.ota.domain.AdvertisementMapping;

public interface AdvertisementMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    int insert(AdvertisementMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    int insertSelective(AdvertisementMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    AdvertisementMapping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AdvertisementMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisementmapping
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AdvertisementMapping record);

	int selectlastid();
}