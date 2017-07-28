package com.ota.handlers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.UserMapper;
import com.ota.domain.User;

@Controller
public class LoginHandler {
	@Autowired
	private UserMapper userMapper;
	
//	@Autowired
//	private BusinessMapper bmp;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/checkin", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(@RequestBody User user) {
		ModelMap mv = new ModelMap();
		String msgcode = null;
		User userIndatabase = userMapper.findByUser(user);
		if(userIndatabase == null) {
			//密码或账户名错误
			msgcode = "-1";
		} else {
			msgcode = "1";
			session.setAttribute("role", userIndatabase.getRole());
			mv.put("role", userIndatabase.getRole());
			mv.put("href", "index.html");
		}
		mv.put("msgcode", msgcode);
		return mv;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		System.out.println("index");
		return new ModelAndView("index");
	}
	
	@RequestMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("logout")
	@ResponseBody
	public JSONObject logout() {
		JSONObject json = new JSONObject();
		session.invalidate();
		json.put("code", 1);
		return json;
	}
	
}
