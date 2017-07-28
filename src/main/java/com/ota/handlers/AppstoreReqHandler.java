package com.ota.handlers;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ota.dao.AppstoreMapper;
import com.ota.dao.BusinessMapper;
import com.ota.domain.AppstoreInfo;
import com.ota.tools.OTAToolKit;

@Controller
public class AppstoreReqHandler {
	@Autowired
	private AppstoreMapper appstoreMapper;
	@Autowired
	private BusinessMapper businessMapper;

	@RequestMapping("appStore")
	@ResponseBody
	public JSONArray appStoreReq(AppstoreInfo appstoreInfo, HttpServletRequest request) throws UnknownHostException {
		JSONArray jsonArray = new JSONArray();
		String reqIP = request.getRemoteAddr();
		//String serverAddr = request.getLocalAddr();
		int serverPort = request.getLocalPort();
		String appVersion = appstoreInfo.getAppVersion();
		String homeui = appstoreInfo.getHomeUI();
		String userid = appstoreInfo.getUserID();
		if (appVersion == null)
			appVersion = "";
		if (homeui == null)
			homeui = "";
		if (userid == null)
			userid = "";

		List<AppstoreInfo> appstoreInfoList = appstoreMapper.selectAppstoreInfo(appstoreInfo);
		
		
		for (AppstoreInfo appstoreInfoInList : appstoreInfoList) {
			System.out.println(appstoreInfoInList.toString());
			boolean ismatchUserID = OTAToolKit.matchFromLeft(userid, appstoreInfoInList.getUserID());
			boolean ismatchIP = OTAToolKit.matchFromLeft(reqIP, appstoreInfoInList.getIpbegin())
					|| OTAToolKit.matchFromLeft(reqIP, appstoreInfoInList.getIpbegin());

			if (ismatchUserID
					&& (appstoreInfoInList.getAppVersion() == null || appstoreInfoInList.getAppVersion().length() <= 0
							|| appVersion.equals(appstoreInfoInList.getAppVersion()))
					&& (appstoreInfoInList.getHomeUI() == null || appstoreInfoInList.getHomeUI().length() <= 0
							|| homeui.equals(appstoreInfo.getHomeUI()))
					&& (appstoreInfoInList.getDVBSupport() == null || (appstoreInfo.getDVBSupport() != null
							&& (!(appstoreInfo.getDVBSupport() ^ appstoreInfoInList.getDVBSupport()))))) {
				JSONObject json = new JSONObject();
				
				String apkdownloadUrlInDataBase = appstoreInfoInList.getApkDownload();
				String apkdownloadUrlForRequst = OTAToolKit.getAddr(serverPort, apkdownloadUrlInDataBase);
				String IcondownloadUrlInDataBase = appstoreInfoInList.getApkIcon();
				String IcondownloadUrlForRequst = OTAToolKit.getAddr(serverPort, IcondownloadUrlInDataBase);
				
				json.put("apkName", appstoreInfoInList.getApkName());
				json.put("apkIcon", IcondownloadUrlForRequst);
				json.put("apkVersion", appstoreInfoInList.getAppStoreVersion());
				json.put("apkInfo", appstoreInfoInList.getApkinfo());
				json.put("apkDownload", apkdownloadUrlForRequst);
				jsonArray.add(json);
			}
		}

		return jsonArray;

	}
}
