package com.ota.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ota.dao.AppLogMapper;
import com.ota.domain.AppLog;
import com.ota.domain.OTALog;
import com.ota.tools.OTAToolKit;

@Controller
public class APPLogHandlers {
	@Autowired
	private AppLogMapper appLogMapper;

	@RequestMapping(value = "applog-info")
	@ResponseBody
	public Map<String, Object> otalogRequest(HttpServletRequest request) {
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String customer = request.getParameter("customer");
		String model = request.getParameter("model");
		String action = request.getParameter("action");
		
		if (customer.equals("All Customer"))
			customer = null;
		if (model.equals("All Model"))
			model = null;
		if(action.equals("All Action"))
			action = null;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			map.put("customer", customer);
			map.put("model", model);
			map.put("action", action);
			
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			List<OTALog> otaLogs;
			otaLogs = appLogMapper.selectAppLogsWithFilter(map, pageBounds);
			int total = appLogMapper.countAppLogsWithFilter(map);
			
			resultMap.put("total", total);
			resultMap.put("rows", otaLogs);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resultMap;
	}
	@RequestMapping(value="applogrequest",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject otalogRequest(AppLog appLog) {
		JSONObject jsonObject = new JSONObject();
		String standartTimeString = OTAToolKit.string2StandardFormOfTimeString(appLog.getLogtime());
		appLog.setLogtime(standartTimeString);
		try {
			appLogMapper.insertSelective(appLog);
			jsonObject.put("code", "1");
			jsonObject.put("msg", "success!");
		} catch(Exception exception) {
			exception.printStackTrace();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "error in upload log!");
			
		}
		//http://localhost:8080/ota/applogrequest?customer=customer01&model=random&device=idonotknow&appname=otamobile&packagename=whateveryouwant&version=0.01&logtime=20171012030233&action=ssdased
		return jsonObject;
	}

}
