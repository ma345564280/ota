package com.ota.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ota.domain.User;
import com.ota.service.UserService;
import com.ota.dao.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper userDao;
	
	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public Set<String> getRoles(String userName) {
		return userDao.getRoles(userName);
	}

	public Set<String> getPermissions(String userName) {
		return userDao.getPermissions(userName);
	}

}
