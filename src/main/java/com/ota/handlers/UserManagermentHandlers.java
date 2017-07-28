package com.ota.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BusinessMapper;
import com.ota.dao.RoleMapper;
import com.ota.dao.UserMapper;
import com.ota.domain.Role;
import com.ota.domain.User;

@Controller
public class UserManagermentHandlers {

	@Autowired
	UserMapper um;
	@Autowired
	BusinessMapper bsm;
	@Autowired
	RoleMapper roleMapper;
	@RequestMapping("user-info")
	@ResponseBody
	public List<User> userInfo() {
		List<User> userList = um.selectAllUser();
		return userList;
	}


	@RequestMapping(value="check-account")
	@ResponseBody
	public JSONObject checkAccount(@RequestBody JSONObject jsonRec){
		JSONObject json = new JSONObject();
		String account = jsonRec.getString("username");
		
		User user = um.selectUserByusername(account);
		
		if(user != null) {
			json.put("code", "-1");
			json.put("msg", "Account existed!");
		} else {
			json.put("code", "1");
			json.put("msg", "Account not exists!");
		}
		
		return json;
		
		
	}
	
	@RequestMapping("add-account")
	@ResponseBody
	public JSONObject addAccount(@RequestBody User user) {
		JSONObject json = new JSONObject();
		try {
			//String password = user.getPassword();
			//user.setPassword(MD5.GetMD5Code(password));
			System.out.println(user.toString());
			um.save(user);
			
			json.put("code", "1");
			json.put("msg", "success");

		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in inserting");
		}
		return json;
	}
	
	
	@RequestMapping("deleteAccount")
	@ResponseBody
	public JSONObject deleteAccount(@RequestBody User user) {
		JSONObject json = new JSONObject();
		try{
			um.delete(user);
			json.put("code", "1");
			json.put("msg", "success!");
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in delete");
		}
		
		return json;
	}
	
	@RequestMapping("updateAccount")
	@ResponseBody
	public JSONObject updateAccount(@RequestBody JSONObject jsonObject) {
		JSONObject json = new JSONObject();
		String username = jsonObject.getString("account");
		int id = Integer.parseInt(jsonObject.getString("id"));
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setRole(jsonObject.getString("role"));
		user.setPassword(jsonObject.getString("password"));
		try{
			um.update(user);
			json.put("code", "1");
			json.put("msg", "success!");
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
			json.put("msg", "error in update");
		}
		return json;
	}
	
	@RequestMapping("role-info")
	@ResponseBody
	public List<Role> queryRoleInfo() {
		List<Role> roles = roleMapper.selectAllRoles();
		return roles;
	}
}
