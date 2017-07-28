package com.ota.dao;

import java.util.List;

import com.ota.domain.Role;
import com.ota.domain.User;
import com.ota.domain.UserWithCom;

public interface UserMapper {
	void save(User user);
	User findByUser(User user);
	List<UserWithCom> selectAllUserWithCom();
	void delete(User user);
	User selectUserByusername(String username);
	void update(User user);
	List<User> selectAllUser();
	int countUserInThisRole(String role);
}
