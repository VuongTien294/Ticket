package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.UserDTO;

public interface UserService {

	UserDTO getUserById(Long id);

	void deleteUser(Long id);

	void updateUser(UserDTO userDTO);

	void addUser(UserDTO userDTO);

	void setupUserPassword(UserDTO accountDTO);

	void changePassword(UserDTO userDTO);

	UserDTO forgetPassword(String email);



}
