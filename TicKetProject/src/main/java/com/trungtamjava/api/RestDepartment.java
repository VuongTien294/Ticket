package com.trungtamjava.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trungtamjava.model.DepartmentDTO;
import com.trungtamjava.model.ResponseDTO;
import com.trungtamjava.model.SearchDepartmentDTO;
import com.trungtamjava.service.DepartmentService;

@RestController
@RequestMapping("/api")
public class RestDepartment {
	@Autowired
	private DepartmentService departmentService;

	//Chuc nang them phong ban
	@PostMapping("/admin/department/add")
	public DepartmentDTO add(@RequestBody DepartmentDTO departmentDTO) {
		departmentService.add(departmentDTO);
		return departmentDTO;
	}

	//Chuc nang sua thong tin phong ban
	@PutMapping(value = "/admin/department/update")
	public void update(@RequestBody DepartmentDTO departmentDTO) {
		departmentService.update(departmentDTO);
	}

	//Xoa 1 phong ban
	@DeleteMapping(value = "/admin/department/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		departmentService.delete(id);
	}

	//Lay thong tin 1 phong ban
	@GetMapping(value = "/department/{id}")
	public DepartmentDTO get(@PathVariable(name = "id") Long id) {
		return departmentService.getId(id);
	}

	//Chuc nang tim kiem du an
	@PostMapping(value = "/department/search")
	public ResponseDTO<DepartmentDTO> find(@RequestBody SearchDepartmentDTO searchDepartmentDTO) {
		ResponseDTO<DepartmentDTO> responseDTO = new ResponseDTO<DepartmentDTO>();
		responseDTO.setData(departmentService.find(searchDepartmentDTO));
		responseDTO.setRecordsFiltered(departmentService.count(searchDepartmentDTO));
		responseDTO.setRecordsTotal(departmentService.countTotal(searchDepartmentDTO));
		return responseDTO;
	}
	
	

}
