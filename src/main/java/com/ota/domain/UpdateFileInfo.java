package com.ota.domain;

import java.util.Date;

public class UpdateFileInfo {
	private Integer id;
	private String download;
	private String name;
	private String groupname;
	private String type;
	private String fileVersion;
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
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
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
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
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
		return "UpdateFileInfo [id=" + id + ", download=" + download + ", name=" + name + ", groupname=" + groupname
				+ ", type=" + type + ", fileVersion=" + fileVersion + ", enforce=" + enforce + ", updateTime="
				+ updateTime + ", status=" + status + ", description=" + description + ", mappingid=" + mappingid + "]";
	}
	
	
}
