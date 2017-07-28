package com.ota.domain;

public class UpdatefileAndFilter {
	private Integer id;
	private String mappingid;
	private String description;
	private String businessid;

	private String romversion;
	private String type;
	private Boolean isCompulsive;
	private String userid;
	private String appversion;
	private String hardwareversion;
	private String manufacturer;
	private Boolean dvbsupport;
	private String homeui;
	private String ipbegin;
	private String ipend;
	private String customName;
	
	public Updatefile getUpdateFile() {
		return new Updatefile(this.getId(),this.getMappingid());
	}
	
	public FileMapping getFileMapping(Integer modelid)	{
		return new FileMapping(
				this.getRomversion(),
				modelid,
				this.getIsCompulsive(),
				this.getUserid(),
				this.getAppversion(),
				this.getHardwareversion(),
				this.manufacturer,
				this.getDvbsupport(),
				this.getHomeui(),
				this.getIpbegin(),
				this.getIpend()
				);
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMappingid() {
		return mappingid;
	}

	public void setMappingid(String mappingid) {
		this.mappingid = mappingid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRomversion() {
		return romversion;
	}

	public void setRomversion(String romversion) {
		this.romversion = romversion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsCompulsive() {
		return isCompulsive;
	}

	public void setIsCompulsive(Boolean isCompulsive) {
		this.isCompulsive = isCompulsive;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getHardwareversion() {
		return hardwareversion;
	}

	public void setHardwareversion(String hardwareversion) {
		this.hardwareversion = hardwareversion;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Boolean getDvbsupport() {
		return dvbsupport;
	}

	public void setDvbsupport(Boolean dvbsupport) {
		this.dvbsupport = dvbsupport;
	}

	public String getHomeui() {
		return homeui;
	}

	public void setHomeui(String homeui) {
		this.homeui = homeui;
	}

	public String getIpbegin() {
		return ipbegin;
	}

	public void setIpbegin(String ipbegin) {
		this.ipbegin = ipbegin;
	}

	public String getIpend() {
		return ipend;
	}

	public void setIpend(String ipend) {
		this.ipend = ipend;
	}
}
