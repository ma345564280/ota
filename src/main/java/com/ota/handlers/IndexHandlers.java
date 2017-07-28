package com.ota.handlers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BusinessMapper;
import com.ota.domain.Business;

@Controller
public class IndexHandlers {

	@Autowired
	private BusinessMapper businessMapper;
	
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping("getIP")
	@ResponseBody
	public JSONObject getIP() {
		System.out.println("getIP");
		JSONObject json = new JSONObject();
		String businessid = (String) session.getAttribute("businessid");
		try {
			Business business = businessMapper.selectByPrimaryKey(Integer.parseInt(businessid));
			json.put("serverip", business.getServerip());
			json.put("code", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
		}
		return json;
	}
	
	@RequestMapping("setIP")
	@ResponseBody
	public JSONObject setIP(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String serverip = jsonRec.getString("ip");
		Integer businessid = (Integer) session.getAttribute("businessid"); 
		Business business = new Business();
		business.setId(businessid);
		business.setServerip(serverip);
		try {
			businessMapper.updateByPrimaryKeySelective(business);
			json.put("code", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
		}
		return json;
	}
}
