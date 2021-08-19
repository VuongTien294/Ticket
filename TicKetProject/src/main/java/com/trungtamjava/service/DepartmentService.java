package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.DepartmentDTO;
import com.trungtamjava.model.SearchDepartmentDTO;

public interface DepartmentService {

	Long countTotal(SearchDepartmentDTO searchCategoryDTO);

	Long count(SearchDepartmentDTO searchCategoryDTO);

	List<DepartmentDTO> find(SearchDepartmentDTO searchCategoryDTO);

	DepartmentDTO getId(Long id);

	void update(DepartmentDTO categoryDTO);

	void delete(Long id);

	void add(DepartmentDTO category);



}
