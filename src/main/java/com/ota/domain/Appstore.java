package com.ota.domain;

import java.util.Date;

public class Appstore {
	
	
    public Appstore(Integer id, String mappingid) {
		super();
		this.id = id;
		this.mappingid = mappingid;
	}
    
    public Appstore() {
    	super();
	}
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.appstoreversion
     *
     * @mbggenerated
     */
    private String appstoreversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.apkdownload
     *
     * @mbggenerated
     */
    private String apkdownload;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.apkname
     *
     * @mbggenerated
     */
    private String apkname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.apkicon
     *
     * @mbggenerated
     */
    private String apkicon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.mappingid
     *
     * @mbggenerated
     */
    private String mappingid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.businessid
     *
     * @mbggenerated
     */
    private Integer businessid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.apkversion
     *
     * @mbggenerated
     */
    private String apkversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appstore.apkinfo
     *
     * @mbggenerated
     */
    private String apkinfo;



	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.id
     *
     * @return the value of appstore.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.id
     *
     * @param id the value for appstore.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.appstoreversion
     *
     * @return the value of appstore.appstoreversion
     *
     * @mbggenerated
     */
    public String getAppstoreversion() {
        return appstoreversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.appstoreversion
     *
     * @param appstoreversion the value for appstore.appstoreversion
     *
     * @mbggenerated
     */
    public void setAppstoreversion(String appstoreversion) {
        this.appstoreversion = appstoreversion == null ? null : appstoreversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.update_time
     *
     * @return the value of appstore.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.update_time
     *
     * @param updateTime the value for appstore.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.apkdownload
     *
     * @return the value of appstore.apkdownload
     *
     * @mbggenerated
     */
    public String getApkdownload() {
        return apkdownload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.apkdownload
     *
     * @param apkdownload the value for appstore.apkdownload
     *
     * @mbggenerated
     */
    public void setApkdownload(String apkdownload) {
        this.apkdownload = apkdownload == null ? null : apkdownload.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.apkname
     *
     * @return the value of appstore.apkname
     *
     * @mbggenerated
     */
    public String getApkname() {
        return apkname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.apkname
     *
     * @param apkname the value for appstore.apkname
     *
     * @mbggenerated
     */
    public void setApkname(String apkname) {
        this.apkname = apkname == null ? null : apkname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.apkicon
     *
     * @return the value of appstore.apkicon
     *
     * @mbggenerated
     */
    public String getApkicon() {
        return apkicon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.apkicon
     *
     * @param apkicon the value for appstore.apkicon
     *
     * @mbggenerated
     */
    public void setApkicon(String apkicon) {
        this.apkicon = apkicon == null ? null : apkicon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.mappingid
     *
     * @return the value of appstore.mappingid
     *
     * @mbggenerated
     */
    public String getMappingid() {
        return mappingid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.mappingid
     *
     * @param mappingid the value for appstore.mappingid
     *
     * @mbggenerated
     */
    public void setMappingid(String mappingid) {
        this.mappingid = mappingid == null ? null : mappingid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.businessid
     *
     * @return the value of appstore.businessid
     *
     * @mbggenerated
     */
    public String getApkversion() {
        return apkversion;
    }

    public Integer getBusinessid() {
		return businessid;
	}

	public void setBusinessid(Integer businessid) {
		this.businessid = businessid;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.apkversion
     *
     * @param apkversion the value for appstore.apkversion
     *
     * @mbggenerated
     */
    public void setApkversion(String apkversion) {
        this.apkversion = apkversion == null ? null : apkversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appstore.apkinfo
     *
     * @return the value of appstore.apkinfo
     *
     * @mbggenerated
     */
    public String getApkinfo() {
        return apkinfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appstore.apkinfo
     *
     * @param apkinfo the value for appstore.apkinfo
     *
     * @mbggenerated
     */
    public void setApkinfo(String apkinfo) {
        this.apkinfo = apkinfo == null ? null : apkinfo.trim();
    }

	@Override
	public String toString() {
		return "Appstore [id=" + id + ", appstoreversion=" + appstoreversion + ", updateTime=" + updateTime
				+ ", apkdownload=" + apkdownload + ", apkname=" + apkname + ", apkicon=" + apkicon + ", mappingid="
				+ mappingid + ", businessid=" + businessid + ", apkversion=" + apkversion + ", apkinfo=" + apkinfo
				+ "]";
	}
    
    
    
    
    
}