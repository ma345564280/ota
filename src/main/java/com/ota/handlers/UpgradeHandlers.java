package com.ota.handlers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.FileMappingMapper;
import com.ota.dao.UpdatefileMapper;
import com.ota.domain.Box;
import com.ota.domain.FileMapping;
import com.ota.domain.Updatefile;
import com.ota.tools.OTAToolKit;

@Controller
public class UpgradeHandlers {

	@Autowired
	private UpdatefileMapper ufm;
	@Autowired
	private FileMappingMapper fmm;
	@Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private BoxTypeMapper boxTypeMapper;
	// http://localhost:8080/ota/upgrade?MAC=0&Type=1.0&RomVersion=0.9&AppVersion=1.0&HardwareVersion=1.0&Manufacturer=1.0&DVBSupport=false&HomeUI=1&UserID=1&CustomName=default
	//http://47.90.127.105:8080/ota/upgrade?Type=Etimo@T2&RomVersion=1.0.4&ApkVersion=20170712_androidM_DVB_sample&HardwareVersion=V1.0&Manufacturer=Amlogic&DVBSupport=true&HomeUI=&Mac=22:22:22:11:11:55&UserID=201100001111 
	@RequestMapping("upgrade")
	@ResponseBody
	public JSONObject upgradeRequest(HttpServletRequest req) {

		//String serverAddress = req.getLocalAddr();
		int serverPort = req.getServerPort();
		String reqIP = req.getRemoteAddr();

		String mac = (String) req.getParameter("MAC");
		if (mac == null) {
			mac = "";
		}
		
		String type = (String) req.getParameter("Type");
		Integer modelid = null;
		if (type == null) {
			type = "";
		} else {
			modelid = boxTypeMapper.selectModelIDByType(type);
		}
		String appVersion = (String) req.getParameter("ApkVersion");
		if (appVersion == null) {
			appVersion = "";
		}
		String romVersion = (String) req.getParameter("RomVersion");
		if (romVersion == null) {
			romVersion = "";
		}
		String hardwareVersion = (String) req.getParameter("HardwareVersion");
		if (hardwareVersion == null) {
			hardwareVersion = "";
		}
		String manufacturer = (String) req.getParameter("Manufacturer");
		if (manufacturer == null) {
			manufacturer = "";
		}
		
		Boolean DVBSupport = null;
		if (req.getParameter("DVBSupport") == null) {
			DVBSupport = null;
		} else {
			DVBSupport = req.getParameter("DVBSupport").equals("true") ? Boolean.TRUE : Boolean.FALSE;
		}
		String homeUI = (String) req.getParameter("HomeUI");
		if (homeUI == null) {
			homeUI = "";
		}
		String userID = (String) req.getParameter("UserID");
		if (userID == null) {
			userID = "";
		}
		// String customName = (String) req.getAttribute("CustomName");
		Box box = new Box();
		box.setMac(mac);
		box.setModelid(modelid);
		box.setAppversion(appVersion);
		box.setRomversion(romVersion);
		box.setHardwareversion(hardwareVersion);
		box.setManufacturer(manufacturer);
		box.setDvbsupport(DVBSupport);
		box.setHomeui(homeUI);
		box.setUserid(userID);

		List<FileMapping> filemappingList = fmm.selectByTypeAndRomVersion(box);
		JSONObject jsonupdateinfo = new JSONObject();

		if (filemappingList.isEmpty() || filemappingList == null) {
			jsonupdateinfo.put("msgcode", "-1");
			return jsonupdateinfo;
		}

		for (FileMapping fm : filemappingList) {
			boolean ismatchIP = OTAToolKit.matchFromLeft(reqIP, fm.getIpbegin())
					|| OTAToolKit.matchFromLeft(reqIP, fm.getIpbegin());
			boolean ismatchUserID = OTAToolKit.matchFromLeft(userID, fm.getUserid());
			try{
				if (
						(fm.getAppversion() == null || fm.getAppversion().equals("") || fm.getAppversion().equals(appVersion))
						&& (fm.getHardwareversion() == null || fm.getHardwareversion().equals("") || fm.getHardwareversion().equals(hardwareVersion))
						&& (fm.getManufacturer() == null || fm.getManufacturer().equals("") || fm.getManufacturer().equals(manufacturer)) && ismatchUserID
						&& (fm.getDvbsupport() == null || (DVBSupport != null && (!(fm.getDvbsupport() ^ DVBSupport))))
						&& (fm.getHomeui() == null || fm.getHomeui().equals("") || fm.getHomeui().equals(homeUI))) {
					Updatefile upfl = ufm.selectByMappingid(fm);
					jsonupdateinfo.put("msgcode", "1");
					jsonupdateinfo.put("updateversion", upfl.getFileVersion());
					jsonupdateinfo.put("description", upfl.getDescription());
					jsonupdateinfo.put("MD5", upfl.getMd5());
					jsonupdateinfo.put("isCompulsive", fm.getIsCompulsive());
					String fileName = upfl.getDownload();
					String downloadUrl = OTAToolKit.getAddr(serverPort, fileName);

					jsonupdateinfo.put("downloadUrl", downloadUrl);
					jsonupdateinfo.put("description", upfl.getDescription());
					break;
				}
				
			}catch(Exception e) {
				jsonupdateinfo.put("msgcode", "-3");
				e.printStackTrace();
			}
			
		}

		if (!jsonupdateinfo.containsKey("msgcode")) {
			jsonupdateinfo.put("msgcode", "-2");
		}
		return jsonupdateinfo;
	}

}
