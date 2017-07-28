package com.ota.domain;

public class AdvertisementInfo implements Comparable<AdvertisementInfo> {

	private Integer id;
	private String AdVersion;
	private Integer positionid;
	private String download;
	private String gotourl;
	private String mappingid;
	private String businessid;

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	private String CustomeName;

	private String RomVersion;
	private String Type;
	private String UserID;
	private String AppVersion;
	private String hardwareversion;
	private String manufacturer;
	private Boolean DVBSupport;
	private String HomeUI;
	private String ipbegin;
	private String ipend;

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

	public String getCustomeName() {
		return CustomeName;
	}

	public void setCustomeName(String customeName) {
		CustomeName = customeName;
	}

	public String getAdVersion() {
		return AdVersion;
	}

	public void setAdVersion(String adVersion) {
		AdVersion = adVersion;
	}

	public Integer getPositionid() {
		return positionid;
	}

	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getGotourl() {
		return gotourl;
	}

	public void setGotourl(String gotourl) {
		this.gotourl = gotourl;
	}

	public String getRomVersion() {
		return RomVersion;
	}

	public void setRomVersion(String romVersion) {
		RomVersion = romVersion;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
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

	public Boolean getDVBSupport() {
		return DVBSupport;
	}

	public void setDVBSupport(Boolean dVBSupport) {
		DVBSupport = dVBSupport;
	}

	public String getHomeUI() {
		return HomeUI;
	}

	public void setHomeUI(String homeUI) {
		HomeUI = homeUI;
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

	@Override
	public String toString() {
		return "AdvertisementInfo [AdVersion=" + AdVersion + ", positionid=" + positionid + ", download=" + download
				+ ", gotourl=" + gotourl + ", RomVersion=" + RomVersion + ", Type=" + Type + ", UserID=" + UserID
				+ ", AppVersion=" + AppVersion + ", hardwareversion=" + hardwareversion + ", manufacturer="
				+ manufacturer + ", DVBSupport=" + DVBSupport + ", HomeUI=" + HomeUI + ", ipbegin=" + ipbegin
				+ ", ipend=" + ipend + "]";
	}

	public Advertisement getAdvertisement() {

		return new Advertisement(this.id, this.mappingid);
	}

	public AdvertisementMapping getAdvertisementMapping() {

		return new AdvertisementMapping(this.RomVersion, this.Type, this.UserID, this.AppVersion, this.hardwareversion,
				this.manufacturer, this.getDVBSupport(), this.HomeUI, this.ipbegin, this.ipend);
	}

	@Override
	public int compareTo(AdvertisementInfo o) {
		float thisAdversion = Float.parseFloat(this.AdVersion);
		float otherAdversion = Float.parseFloat(o.getAdVersion());

		if ((thisAdversion - otherAdversion) >= 0)
			return -1;
		else
			return 1;

	}

}
