package com.ota.handlers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BoxMapper;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.CustomerGroupMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Box;
import com.ota.tools.OTAToolKit;

@Controller
public class AddBoxHandler {
	@Autowired
	HttpSession session;
	@Autowired
	BoxMapper BoxMapper;
	@Autowired
	BusinessMapper businessMapper;
	@Autowired
	CustomerGroupMapper customerGroupMapper;
	@Autowired
	OTAGroupMapper otaGroupMapper;
	@Autowired
	BoxTypeMapper boxTypeMapper;
	
	@RequestMapping("submitBox")
	@ResponseBody
	public JSONObject addBox(@RequestBody JSONObject macAndUserID) {
		JSONObject json = new JSONObject();
		System.out.println("boxadd");
		String MACBegin = macAndUserID.getString("MACBegin");
		String MACEnd = macAndUserID.getString("MACEnd");
		String UserIDBegin = macAndUserID.getString("UserIDBegin");
		String UserIDEnd = macAndUserID.getString("UserIDEnd");
		String Customer = macAndUserID.getString("Customer");
		String Group = macAndUserID.getString("Group");
		String Model = macAndUserID.getString("Model");
		
		Integer businessid = businessMapper.selectBusinessidByName(Customer);
		Integer groupid = otaGroupMapper.selectGroupIDByGroupName(Group);
		Integer modelid = boxTypeMapper.selectModelIDByType(Model);
		System.out.println("group" + Group);
		Box box;
//		String businessid = (String) session.getAttribute("businessid");
		long mac = Long.parseLong(MACBegin.replace(":", ""), 16);
		String macString = OTAToolKit.tranferToMac(Long.toHexString(mac));
		long i = 0;
		try {
			for(i = Long.parseLong(UserIDBegin); i <= Long.parseLong(UserIDEnd); i++) {
				box = new Box();
				box.setBusinessid(businessid);
				box.setMac(macString);
				box.setUserid(String.valueOf(i));
				box.setGroupid(groupid);
				box.setModelid(modelid);
				System.out.println(box.toString());
				BoxMapper.insertSelective(box);
				
				mac += 1;
				macString = OTAToolKit.tranferToMac(Long.toHexString(mac));
			}
			
			json.put("code", 1);
			json.put("msg", "success");
			
		} catch (Exception e) {
			json.put("erroInInsertUserID",i);
			json.put("erroInMac",macString);
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		
		return json;
	}
}
