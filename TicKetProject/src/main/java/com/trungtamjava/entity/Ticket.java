package com.trungtamjava.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket extends CreateAuditable implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	public Long id;
	
	public int number;
	
	//cau hoi cua khach hang
	public String question;
	
	//tra loi khach hang
	public String answer;
	
	//trang thai cua ticket.Neu la enabled thi nghia la chua co ai tra loi khach
	private Boolean enabled;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public User user;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	public Department department;

}