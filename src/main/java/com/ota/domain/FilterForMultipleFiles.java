package com.ota.domain;

public class FilterForMultipleFiles {
	private String ids;

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCustomeName() {
		return CustomeName;
	}

	public void setCustomeName(String customeName) {
		CustomeName = customeName;
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

	
	public AdvertisementMapping getAdvertisementMapping() {

		return new AdvertisementMapping(
				this.RomVersion,
				this.Type,
				this.UserID,
				this.AppVersion,
				this.hardwareversion,
				this.manufacturer,
				this.getDVBSupport(),
				this.HomeUI,
				this.ipbegin,
				this.ipend
				);
	}
	
	
	public AppstoreMapping getAppstoreMapping() {

		return new AppstoreMapping(
				this.RomVersion,
				this.Type,
				this.UserID,
				this.AppVersion,
				this.hardwareversion,
				this.manufacturer,
				this.getDVBSupport(),
				this.HomeUI,
				this.ipbegin,
				this.ipend
				);
	}
	
	@Override
	public String toString() {
		return "FilterForMultipleFiles [ids=" + ids + ", CustomeName=" + CustomeName + ", RomVersion=" + RomVersion
				+ ", Type=" + Type + ", UserID=" + UserID + ", AppVersion=" + AppVersion + ", hardwareversion="
				+ hardwareversion + ", manufacturer=" + manufacturer + ", DVBSupport=" + DVBSupport + ", HomeUI="
				+ HomeUI + ", ipbegin=" + ipbegin + ", ipend=" + ipend + "]";
	}
	
	

}
