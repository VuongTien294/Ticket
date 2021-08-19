package com.trungtamjava.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String age;
	
	private List<String> roles;
	private Boolean enabled;
	private String username;
	private String password;
	private String address;
	private String gender;
	private String phone;
	private String email;
	
	private String createdDate;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Long id) {
		super();
		this.id = id;
	}
}
