package com.ota.handlers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ota.dao.AdvertisementMapper;
import com.ota.dao.BusinessMapper;
import com.ota.domain.AdvertisementInfo;
import com.ota.tools.OTAToolKit;

@Controller
public class AdReqHandler {
	@Autowired
	private AdvertisementMapper admp;
	@Autowired
	private BusinessMapper businessMapper;
//http://localhost:8080/ota/advertisement?Type=Etimo@T2&RomVersion=1.0.4&ApkVersion=20170712_androidM_DVB_sample&HardwareVersion=V1.0&Manufacturer=Amlogic&DVBSupport=true&HomeUI=&Mac=22:22:22:11:11:55&UserID=201100001111
	@RequestMapping("advertisement")
	@ResponseBody
	public JSONArray adRequest(AdvertisementInfo adInfo, HttpServletRequest request) throws UnknownHostException {
		JSONArray jsonArray = new JSONArray();
		String reqIP = request.getRemoteAddr();
		//String serverAddr = request.getLocalAddr();
		int serverPort = request.getLocalPort();
		String appVersion = adInfo.getAppVersion();
		String homeui = adInfo.getHomeUI();
		String userid = adInfo.getUserID();
		if (appVersion == null)
			appVersion = "";
		if (homeui == null)
			homeui = "";
		if (userid == null)
			userid = "";
		System.out.println(adInfo.toString());
		List<AdvertisementInfo> adInfoList = admp.selectAdvertisementInfo(adInfo);
		List<Integer> SinglePositionID = new ArrayList<>();
		
		Collections.sort(adInfoList);
		
		for (AdvertisementInfo adInfoInList : adInfoList) {
			System.out.println(adInfoInList.toString());
			boolean ismatchUserID = OTAToolKit.matchFromLeft(userid, adInfoInList.getUserID());
			boolean ismatchIP = OTAToolKit.matchFromLeft(reqIP, adInfoInList.getIpbegin())
					|| OTAToolKit.matchFromLeft(reqIP, adInfoInList.getIpbegin());

			if (ismatchUserID
					&& (adInfoInList.getAppVersion() == null || adInfoInList.getAppVersion().length() <= 0
							|| appVersion.equals(adInfoInList.getAppVersion()))
					&& (adInfoInList.getHomeUI() == null || adInfoInList.getHomeUI().length() <= 0
							|| homeui.equals(adInfo.getHomeUI()))
					&& (adInfoInList.getDVBSupport() == null || (adInfo.getDVBSupport() != null
							&& (!(adInfo.getDVBSupport() ^ adInfoInList.getDVBSupport()))))) {
				JSONObject json = new JSONObject();
				if(SinglePositionID.contains(adInfoInList.getPositionid())) continue;
				SinglePositionID.add(adInfoInList.getPositionid());
				
				String downloadUrlInDataBase = adInfoInList.getDownload();
				
				String downloadUrlForRequst = OTAToolKit.getAddr(serverPort, downloadUrlInDataBase);
				json.put("positionID", adInfoInList.getPositionid());
				json.put("download", downloadUrlForRequst);
				json.put("goto", adInfoInList.getGotourl());
				json.put("adversion", adInfoInList.getAdVersion());
				jsonArray.add(json);
			}
		}

		return jsonArray;

	}
}
