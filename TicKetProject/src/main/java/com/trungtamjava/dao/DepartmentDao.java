package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Department;
import com.trungtamjava.model.SearchDepartmentDTO;

public interface DepartmentDao {
	void add(Department department);

	void delete(Department department);

	void update(Department department);

	Long countTotal(SearchDepartmentDTO searchDepartmentDTO);

	Long count(SearchDepartmentDTO searchDepartmentDTO);

	List<Department> find(SearchDepartmentDTO searchDepartmentDTO);

	Department getId(Long id);



}
