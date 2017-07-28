package com.ota.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.RoleMapper;
import com.ota.dao.UserMapper;
import com.ota.domain.Role;

@Controller
public class RoleManagermentHandlers {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("deleteRole")
	@ResponseBody
	public JSONObject deleteAccount(@RequestBody Role role) {
		JSONObject json = new JSONObject();
		try{
			String r = role.getRole();
			int count = userMapper.countUserInThisRole(r);
			if(count == 0) {
				roleMapper.deleteByPrimaryKey(role.getRole());
				json.put("code", "1");
				json.put("msg", "success!");
			} else {
				json.put("code", "0");
				json.put("msg", "error");
			}
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in delete");
		}
		
		return json;
	}
	
	@RequestMapping(value="check-role")
	@ResponseBody
	public JSONObject checkAccount(@RequestBody JSONObject jsonRec){
		JSONObject json = new JSONObject();
		String role = jsonRec.getString("role");
		
		int count  = roleMapper.checkNewRole(role);
		if(count > 0) {
			json.put("code", "-1");
			json.put("msg", "Account existed!");
		} else {
			json.put("code", "1");
			json.put("msg", "Account not exists!");
		}
		
		return json;
		
		
	}
	@RequestMapping("add-role")
	@ResponseBody
	public JSONObject addAccount(@RequestBody Role role) {
		JSONObject json = new JSONObject();
		
		try {
			roleMapper.insertSelective(role);
			json.put("code", "1");
			json.put("msg", "success");

		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in inserting");
		}
		return json;
	}
	
	
	@RequestMapping("edit-role")
	@ResponseBody
	public JSONObject updateAccount(@RequestBody Role role) {
		JSONObject json = new JSONObject();
		try{
			roleMapper.updateByPrimaryKey(role);
			json.put("code", "1");
			json.put("msg", "success!");
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in update");
		}
		return json;
	}
}
