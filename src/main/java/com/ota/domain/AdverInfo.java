package com.ota.domain;

import java.util.Date;

public class AdverInfo {
    private Integer id;

    private String adversion;

    private Date updateTime;

    private String download;

    private Integer positionid;

    private String gotourl;

    private String mappingid;

    private String businessid;
    
    private String name;
    
    private String groupname;

    private String status;
    
    private String enforce;
    
    
    @Override
	public String toString() {
		return "AdverInfo [id=" + id + ", adversion=" + adversion
				+ ", updateTime=" + updateTime + ", download=" + download
				+ ", positionid=" + positionid + ", gotourl=" + gotourl
				+ ", mappingid=" + mappingid + ", businessid=" + businessid
				+ ", name=" + name + ", groupname=" + groupname + ", status="
				+ status + ", enforce=" + enforce + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnforce() {
		return enforce;
	}

	public void setEnforce(String enforce) {
		this.enforce = enforce;
	}

	public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.id
     *
     * @param id the value for advertisement.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.adversion
     *
     * @return the value of advertisement.adversion
     *
     * @mbggenerated
     */
    public String getAdversion() {
        return adversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.adversion
     *
     * @param adversion the value for advertisement.adversion
     *
     * @mbggenerated
     */
    public void setAdversion(String adversion) {
        this.adversion = adversion == null ? null : adversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.update_time
     *
     * @return the value of advertisement.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.update_time
     *
     * @param updateTime the value for advertisement.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.download
     *
     * @return the value of advertisement.download
     *
     * @mbggenerated
     */
    public String getDownload() {
        return download;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.download
     *
     * @param download the value for advertisement.download
     *
     * @mbggenerated
     */
    public void setDownload(String download) {
        this.download = download == null ? null : download.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.positionID
     *
     * @return the value of advertisement.positionID
     *
     * @mbggenerated
     */
    public Integer getPositionid() {
        return positionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.positionID
     *
     * @param positionid the value for advertisement.positionID
     *
     * @mbggenerated
     */
    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.gotoUrl
     *
     * @return the value of advertisement.gotoUrl
     *
     * @mbggenerated
     */
    public String getGotourl() {
        return gotourl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.gotoUrl
     *
     * @param gotourl the value for advertisement.gotoUrl
     *
     * @mbggenerated
     */
    public void setGotourl(String gotourl) {
        this.gotourl = gotourl == null ? null : gotourl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.mappingid
     *
     * @return the value of advertisement.mappingid
     *
     * @mbggenerated
     */
    public String getMappingid() {
        return mappingid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.mappingid
     *
     * @param mappingid the value for advertisement.mappingid
     *
     * @mbggenerated
     */
    public void setMappingid(String mappingid) {
        this.mappingid = mappingid == null ? null : mappingid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column advertisement.businessid
     *
     * @return the value of advertisement.businessid
     *
     * @mbggenerated
     */
    public String getBusinessid() {
        return businessid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column advertisement.businessid
     *
     * @param businessid the value for advertisement.businessid
     *
     * @mbggenerated
     */
    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

}
