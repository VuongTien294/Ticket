package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.DepartmentDao;
import com.trungtamjava.entity.Department;
import com.trungtamjava.model.DepartmentDTO;
import com.trungtamjava.model.SearchDepartmentDTO;
import com.trungtamjava.service.DepartmentService;
import com.trungtamjava.ultil.DateTimeUtils;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDao categoryDao;

	@Override
	public void add(DepartmentDTO category) {
		Department categoryEntity = new Department();
		categoryEntity.setName(category.getName());
		categoryDao.add(categoryEntity);

		category.setId(categoryEntity.getId());
	}

	@Override
	public void delete(Long id) {
		Department category = categoryDao.getId(id);
		if (category != null)
			categoryDao.delete(category);

	}

	@Override
	public void update(DepartmentDTO categoryDTO) {
		Department category = categoryDao.getId(categoryDTO.getId());
		if (category != null) {
			category.setName(categoryDTO.getName());
			categoryDao.update(category);
		}

	}

	@Override
	public DepartmentDTO getId(Long id) {
		Department category = categoryDao.getId(id);
		return convertToDTO(category);
	}

	@Override
	public List<DepartmentDTO> find(SearchDepartmentDTO searchCategoryDTO) {
		List<DepartmentDTO> categoryDTOs = new ArrayList<DepartmentDTO>();
		List<Department> categories = categoryDao.find(searchCategoryDTO);
		for (Department category : categories) {
			categoryDTOs.add(convertToDTO(category));
		}

		return categoryDTOs;
	}

	private DepartmentDTO convertToDTO(Department category) {
		DepartmentDTO categoryDTO = new DepartmentDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setCreatedDate(DateTimeUtils.formatDate(category.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		categoryDTO.setName(category.getName());
		return categoryDTO;
	}

	@Override
	public Long count(SearchDepartmentDTO searchCategoryDTO) {
		return categoryDao.count(searchCategoryDTO);
	}

	@Override
	public Long countTotal(SearchDepartmentDTO searchCategoryDTO) {
		return categoryDao.countTotal(searchCategoryDTO);
	}
}
