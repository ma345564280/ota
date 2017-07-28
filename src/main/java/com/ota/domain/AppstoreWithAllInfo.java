package com.ota.domain;

import java.util.Date;

public class AppstoreWithAllInfo {
	private Integer id;
	private String apkdownload;
	private String apkicon;
	private String name;
	private String groupname;
	private String type;
	private String apkversion;
	private String appstoreversion;
	private String enforce;
	private Date updateTime;
	private String status;
	private String description;
	private String mappingid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApkdownload() {
		return apkdownload;
	}
	public void setApkdownload(String apkdownload) {
		this.apkdownload = apkdownload;
	}
	public String getApkicon() {
		return apkicon;
	}
	public void setApkicon(String apkicon) {
		this.apkicon = apkicon;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApkversion() {
		return apkversion;
	}
	public void setApkversion(String apkversion) {
		this.apkversion = apkversion;
	}
	public String getAppstoreversion() {
		return appstoreversion;
	}
	public void setAppstoreversion(String appstoreversion) {
		this.appstoreversion = appstoreversion;
	}
	public String getEnforce() {
		return enforce;
	}
	public void setEnforce(String enforce) {
		this.enforce = enforce;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMappingid() {
		return mappingid;
	}
	public void setMappingid(String mappingid) {
		this.mappingid = mappingid;
	}
	@Override
	public String toString() {
		return "AppstoreWithAllInfo [id=" + id + ", apkdownload=" + apkdownload + ", apkicon=" + apkicon + ", name="
				+ name + ", groupname=" + groupname + ", type=" + type + ", apkversion=" + apkversion
				+ ", appstoreversion=" + appstoreversion + ", enforce=" + enforce + ", updateTime=" + updateTime
				+ ", status=" + status + ", description=" + description + ", mappingid=" + mappingid + "]";
	}
	

}
