package com.trungtamjava.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User extends CreateAuditable{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@NonNull
	   public Long id;
		
		@Column(name = "name")
	   public String name;
		
		@Column(name = "age")
		private String age;
		
		@Column(name = "username",unique = true)
	   public String username;
		
		@Column(name = "password")
	   public String password;
		
		@Column(name = "gender")
		private String gender;

		@Column(name = "address")
		private String address;

		@ElementCollection
		@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {
				@UniqueConstraint(columnNames = { "user_id", "role" }) })
		@Column(name = "role")
		private List<String> roles;

		@Column(name = "phone")
		private String phone;
		
		@Column(unique = true)
		private String email;
		
		@Column(name = "enabled")
		private Boolean enabled;
		
		public User() {
			super();
		}
		
		public User(Long id) {
			super();
			this.id = id;
		}
		
	


}
