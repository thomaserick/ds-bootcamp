package com.devsuperior.bds01.servies;

import com.devsuperior.bds01.dto.DepartamentDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService
{

	@Autowired
	private DepartmentRepository departmentRepository;

	@Transactional(readOnly = true)
	public List<DepartamentDTO> findAll()
	{
		List<Department> list = departmentRepository.findAll(Sort.by("name"));
		return list.stream().map(department -> new DepartamentDTO(department))
			.collect(Collectors.toList());

	}
}
