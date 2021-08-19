package com.trungtamjava.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends CreateAuditable implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	public Long id;
	
	@Column(name = "name",unique = true)
    public String name;
	
	
	
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private Set<Ticket> listticket = new HashSet<Ticket>();
}
