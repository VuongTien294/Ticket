package com.trungtamjava.model;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	public Long id;
	public int number;
	public Boolean enabled;
	public String question;
	public String answer;
	public Long departmentId;
	public String departmentName;
	
	public Long customerId;
	
	public String createdDate;
	public String finishDate;

}
