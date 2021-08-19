package com.trungtamjava.api;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.exception.JwtCustomException;
import com.trungtamjava.model.ResponseDTO;

import com.trungtamjava.model.TokenDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.security.JwtTokenProvider;
import com.trungtamjava.service.UserService;
import com.trungtamjava.ultil.RoleEnum;
import com.trungtamjava.model.UserPrincipal;

@RestController
@Transactional
@CrossOrigin(origins = "*", maxAge = -1)
@RequestMapping(value = "/api")
public class RestUser {

	@Autowired
	 private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	//chuc nang dang nhap
	//Khi KH muon khieu lai thi phai dang nhap vao he thong voi tu cach member
	//Cac duong dan /member danh cho khach hang
	//Cac duong dan /admin danh cho nhan vien cham soc KH
	@PostMapping("/login")
	public TokenDTO login(@RequestParam(required = true, name = "username") String username,
			@RequestParam(required = true, name = "password") String password,
			@RequestParam(name = "deviceToken", required = false) String token) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username);
		} catch (AuthenticationException e) {
			throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	//Nguoi dung dang ki tai khoan
	@PostMapping("/user/register")
	public UserDTO register(@RequestBody UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setRoles(Arrays.asList("ROLE_" + RoleEnum.MEMBER.name()));
		userService.addUser(userDTO);
		return userDTO;
	}

	//Admin dang ki tai khoan cho nhan vien cac phong
	@PostMapping("/admin/user/add")
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.addUser(userDTO);
		return userDTO;
	}

	@PostMapping(value = "/user/update")
	public void updateUser(@RequestBody UserDTO userDTO) {
		userService.updateUser(userDTO);
	}
	
	//Phuong thuc tra lai password khi quen
	@GetMapping(value = "/user/{email}")
	public UserDTO get(@PathVariable(name = "email") String email) {
		return userService.forgetPassword(email);
	}

	@GetMapping(value = "/user/{id}")
	public UserDTO get(@PathVariable(name = "id") Long id) {
		return userService.getUserById(id);
	}

	@DeleteMapping(value = "/admin/user/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		userService.deleteUser(id);
	}
}
