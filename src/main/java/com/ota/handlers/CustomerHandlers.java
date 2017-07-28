package com.ota.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BusinessMapper;
import com.ota.dao.CustomerGroupMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Business;
import com.ota.domain.CustomerGroup;
import com.ota.domain.CustomerGroupDetail;
import com.ota.domain.OTAGroup;

@Controller
public class CustomerHandlers {

	@Autowired
	CustomerGroupMapper customerGroupMapper;
	@Autowired
	BusinessMapper businessMapper;
	@Autowired
	OTAGroupMapper otaGroupMapper;
	
	
	@RequestMapping(value="check-newcustomer")
	@ResponseBody
	public JSONObject checkNew(@RequestBody Business customer){
		JSONObject json = new JSONObject();
		int count = businessMapper.checkNewCustomer(customer);
		if(count == 0) {
			json.put("code", "1");
		} else {
			json.put("code", "-1");
		}
		return json;
	}
	
	@RequestMapping(value="add-customer")
	@ResponseBody
	public JSONObject addnew(@RequestBody Business customer){
		JSONObject json = new JSONObject();
		try {
			businessMapper.insertSelective(customer);
			json.put("code", "1");
		} catch(Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	
	@RequestMapping(value="update-customer")
	@ResponseBody
	public JSONObject editCustomer(@RequestBody Business customer){
		JSONObject json = new JSONObject();
		
		try {
			businessMapper.updateByPrimaryKeySelective(customer);
			json.put("code", "1");
		} catch(Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value="delete-customer")
	@ResponseBody
	public JSONObject deleteCustomer(@RequestBody Business customer){
		JSONObject json = new JSONObject();
		try {
			businessMapper.deleteByPrimaryKey(customer.getId());
			json.put("code", "1");
		} catch(Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
}
