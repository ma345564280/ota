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
import com.ota.dao.OTALogMapper;
import com.ota.domain.OTALog;
import com.ota.tools.OTAToolKit;

@Controller
public class OTALogHandlers {
	@Autowired
	private OTALogMapper otaLogMapper;

	@RequestMapping(value = "otalog-info")
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
			otaLogs = otaLogMapper.selectOTALogsWithFilter(map, pageBounds);
			int total = otaLogMapper.countOTALogsWithFilter(map);
			
			resultMap.put("total", total);
			resultMap.put("rows", otaLogs);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resultMap;
	}

	@RequestMapping(value="otalogrequest",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject otalogRequest(OTALog otaLog) {
		JSONObject jsonObject = new JSONObject();
		String standartTimeString = OTAToolKit.string2StandardFormOfTimeString(otaLog.getLogtime());
		otaLog.setLogtime(standartTimeString);
		try {
			otaLogMapper.insertSelective(otaLog);
			jsonObject.put("code", "1");
			jsonObject.put("msg", "success!");
		} catch(Exception exception) {
			exception.printStackTrace();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "error in upload log!");
			
		}
		return jsonObject;
		//http://localhost:8080/ota/otalogrequest?mac=00:00:00:00:00:01&model=random&currentversion=0.1&updateversion=1.0&ipaddress=192.121.111.111&logtime=20171012233333&action=ssdased
	}
	
	@RequestMapping("get-otalog")
	@ResponseBody
	public List<OTALog> getOTALog() {
		List<OTALog> otaloglist = otaLogMapper.selectAllOTALog();
		return otaloglist;
	}

}
