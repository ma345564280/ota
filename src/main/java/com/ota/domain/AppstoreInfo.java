package com.ota.domain;

public class AppstoreInfo implements Comparable<AppstoreInfo> {

	public AppstoreMapping getAppstoreMapping() {
		return new AppstoreMapping(this.RomVersion, this.Type, this.UserID, this.AppVersion, this.hardwareversion,
				this.manufacturer, this.getDVBSupport(), this.HomeUI, this.ipbegin, this.ipend);
	}

	public Appstore getAppstore() {
		return new Appstore(this.id, this.mappingid);
	}
	


	@Override
	public int compareTo(AppstoreInfo o) {
		float thisAdversion = Float.parseFloat(this.AppStoreVersion);
		float otherAdversion = Float.parseFloat(o.getAppStoreVersion());

		if ((thisAdversion - otherAdversion) >= 0)
			return -1;
		else
			return 1;

	}

	private Integer id;
	private String AppStoreVersion;
	private String ApkDownload;
	private String ApkName;
	private String ApkIcon;
	private String ApkVersion;
	private String mappingid;
	private String apkinfo;
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

	public String getApkinfo() {
		return apkinfo;
	}

	public void setApkinfo(String apkinfo) {
		this.apkinfo = apkinfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getAppStoreVersion() {
		return AppStoreVersion;
	}

	public void setAppStoreVersion(String appStoreVersion) {
		AppStoreVersion = appStoreVersion;
	}

	public String getApkDownload() {
		return ApkDownload;
	}

	public void setApkDownload(String apkDownload) {
		ApkDownload = apkDownload;
	}

	public String getApkName() {
		return ApkName;
	}

	public void setApkName(String apkName) {
		ApkName = apkName;
	}

	public String getApkIcon() {
		return ApkIcon;
	}

	public void setApkIcon(String apkIcon) {
		ApkIcon = apkIcon;
	}

	public String getApkVersion() {
		return ApkVersion;
	}

	public void setApkVersion(String apkVersion) {
		ApkVersion = apkVersion;
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
		return "AppstoreInfo [id=" + id + ", AppStoreVersion=" + AppStoreVersion + ", ApkDownload=" + ApkDownload
				+ ", ApkName=" + ApkName + ", ApkIcon=" + ApkIcon + ", ApkVersion=" + ApkVersion + ", mappingid="
				+ mappingid + ", apkinfo=" + apkinfo + ", CustomeName=" + CustomeName + ", RomVersion=" + RomVersion
				+ ", Type=" + Type + ", UserID=" + UserID + ", AppVersion=" + AppVersion + ", hardwareversion="
				+ hardwareversion + ", manufacturer=" + manufacturer + ", DVBSupport=" + DVBSupport + ", HomeUI="
				+ HomeUI + ", ipbegin=" + ipbegin + ", ipend=" + ipend + "]";
	}

}
