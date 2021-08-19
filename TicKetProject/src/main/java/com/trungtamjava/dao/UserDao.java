package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.User;


public interface UserDao {
	void add(User user);
	
	void update(User user);
	
	void delete(User user);

	User getByName(String name);

	User getByUsername(String username);


	User getUserId(Long id);

	User getByEmail(String email);

}
