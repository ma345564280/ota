package com.ota.domain;

public class FileMappingInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.avaliable_version
     *
     * @mbggenerated
     */
    private String romVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.type
     *
     * @mbggenerated
     */
    private String model;

    public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.is_compulsive
     *
     * @mbggenerated
     */
    private Boolean isCompulsive;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.userid
     *
     * @mbggenerated
     */
    private String userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.businessid
     *
     * @mbggenerated
     */
    private String businessid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.appversion
     *
     * @mbggenerated
     */
    private String appversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.hardwareversion
     *
     * @mbggenerated
     */
    private String hardwareversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.manufacturer
     *
     * @mbggenerated
     */
    private String manufacturer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.dvbsupport
     *
     * @mbggenerated
     */
    private Boolean dvbsupport;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.homeui
     *
     * @mbggenerated
     */
    private String homeui;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.ipbegin
     *
     * @mbggenerated
     */
    private String ipbegin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column filemapping.ipend
     *
     * @mbggenerated
     */
    private String ipend;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.id
     *
     * @return the value of filemapping.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.id
     *
     * @param id the value for filemapping.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

 
    public String getRomVersion() {
		return romVersion;
	}

	public void setRomVersion(String romVersion) {
		this.romVersion = romVersion;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.type
     *
     * @return the value of filemapping.type
     *
     * @mbggenerated
     */

    public Boolean getIsCompulsive() {
        return isCompulsive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.is_compulsive
     *
     * @param isCompulsive the value for filemapping.is_compulsive
     *
     * @mbggenerated
     */
    public void setIsCompulsive(Boolean isCompulsive) {
        this.isCompulsive = isCompulsive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.userid
     *
     * @return the value of filemapping.userid
     *
     * @mbggenerated
     */
    public String getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.userid
     *
     * @param userid the value for filemapping.userid
     *
     * @mbggenerated
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.businessid
     *
     * @return the value of filemapping.businessid
     *
     * @mbggenerated
     */
    public String getBusinessid() {
        return businessid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.businessid
     *
     * @param businessid the value for filemapping.businessid
     *
     * @mbggenerated
     */
    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.appversion
     *
     * @return the value of filemapping.appversion
     *
     * @mbggenerated
     */
    public String getAppversion() {
        return appversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.appversion
     *
     * @param appversion the value for filemapping.appversion
     *
     * @mbggenerated
     */
    public void setAppversion(String appversion) {
        this.appversion = appversion == null ? null : appversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.hardwareversion
     *
     * @return the value of filemapping.hardwareversion
     *
     * @mbggenerated
     */
    public String getHardwareversion() {
        return hardwareversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.hardwareversion
     *
     * @param hardwareversion the value for filemapping.hardwareversion
     *
     * @mbggenerated
     */
    public void setHardwareversion(String hardwareversion) {
        this.hardwareversion = hardwareversion == null ? null : hardwareversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.manufacturer
     *
     * @return the value of filemapping.manufacturer
     *
     * @mbggenerated
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.manufacturer
     *
     * @param manufacturer the value for filemapping.manufacturer
     *
     * @mbggenerated
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.dvbsupport
     *
     * @return the value of filemapping.dvbsupport
     *
     * @mbggenerated
     */
    public Boolean getDvbsupport() {
        return dvbsupport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.dvbsupport
     *
     * @param dvbsupport the value for filemapping.dvbsupport
     *
     * @mbggenerated
     */
    public void setDvbsupport(Boolean dvbsupport) {
        this.dvbsupport = dvbsupport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.homeui
     *
     * @return the value of filemapping.homeui
     *
     * @mbggenerated
     */
    public String getHomeui() {
        return homeui;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.homeui
     *
     * @param homeui the value for filemapping.homeui
     *
     * @mbggenerated
     */
    public void setHomeui(String homeui) {
        this.homeui = homeui == null ? null : homeui.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.ipbegin
     *
     * @return the value of filemapping.ipbegin
     *
     * @mbggenerated
     */
    public String getIpbegin() {
        return ipbegin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.ipbegin
     *
     * @param ipbegin the value for filemapping.ipbegin
     *
     * @mbggenerated
     */
    public void setIpbegin(String ipbegin) {
        this.ipbegin = ipbegin == null ? null : ipbegin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column filemapping.ipend
     *
     * @return the value of filemapping.ipend
     *
     * @mbggenerated
     */
    public String getIpend() {
        return ipend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column filemapping.ipend
     *
     * @param ipend the value for filemapping.ipend
     *
     * @mbggenerated
     */
    public void setIpend(String ipend) {
        this.ipend = ipend == null ? null : ipend.trim();
    }

	public FileMappingInfo() {
		super();
	}

    
    
}