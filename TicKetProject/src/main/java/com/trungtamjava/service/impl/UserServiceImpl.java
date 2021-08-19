package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;

import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.UserService;
import com.trungtamjava.ultil.DateTimeUtils;
import com.trungtamjava.ultil.PasswordGenerator;

@Service
@Transactional
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	public void addUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		if (userDTO.getRoles() != null) {
			user.setRoles(userDTO.getRoles());
		}
		user.setEnabled(userDTO.getEnabled());
		user.setUsername(userDTO.getUsername());
		
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		
		user.setAddress(userDTO.getAddress());
		user.setGender(userDTO.getGender());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		
//		user.setCreatedDate(DateTimeUtils.formatDate(new Date(),DateTimeUtils.DD_MM_YYYY_HH_MM));

		userDao.add(user);
		userDTO.setId(user.getId());
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		User user = userDao.getUserId(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());

			user.setAge(userDTO.getAge());

			user.setUsername(userDTO.getUsername());

//			user.setPassword(userDTO.getPassword());

			user.setAddress(userDTO.getAddress());

			user.setGender(userDTO.getGender());

			user.setPhone(userDTO.getPhone());

			user.setEmail(userDTO.getEmail());

			userDao.update(user);
		}
	}

	@Override
	public void deleteUser(Long id) {
		User user = userDao.getUserId(id);
		if (user != null) {
			userDao.delete(user);
		}
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userDao.getUserId(id);
		return converUserDTO(user);

	}
	


	private UserDTO converUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAge(user.getAge());
		userDTO.setRoles(new ArrayList<String>(user.getRoles()));
		userDTO.setUsername(user.getUsername());

		userDTO.setAddress(user.getAddress());
		userDTO.setGender(user.getGender());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		
		userDTO.setCreatedDate(DateTimeUtils.formatDate(user.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		return userDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,NullPointerException {
		User user = userDao.getByUsername(username.trim());
		
		System.out.println("User Id :"+ user.getId());
		System.out.println("User Name :"+user.getName());
		System.out.println("UserName :"+user.getUsername());
		
		if (user == null) {
			throw new UsernameNotFoundException("Username,Password not found");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

		for (String role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(), true,
				true, true, authorities);
		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setEmail(user.getEmail());
		userPrincipal.setRoles(user.getRoles());
		System.out.println("////UserPrinciable Id :"+userPrincipal.getId());
		
		return userPrincipal;
		
	}
		
	@Override
	public void changePassword(UserDTO userDTO) {
		User user = userDao.getUserId(userDTO.getId());
		if (user != null && PasswordGenerator.checkHashStrings(userDTO.getPassword(), user.getPassword())) {
			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
			userDao.update(user);
		} else {
			throw new DataIntegrityViolationException("wrong password");
		}
	}
	
	
	@Override
	public UserDTO forgetPassword(String email) {
		User user = userDao.getByEmail(email);
		if (user != null) {
			return converUserDTO(user);
		} else {
			throw new DataIntegrityViolationException("wrong password");
		}		
	}

	
	@Override
	public void setupUserPassword(UserDTO accountDTO) {
		User user = userDao.getUserId(accountDTO.getId());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(accountDTO.getPassword()));
		}
	}
}