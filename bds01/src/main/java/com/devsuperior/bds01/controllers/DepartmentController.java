package com.devsuperior.bds01.controllers;


import com.devsuperior.bds01.dto.DepartamentDTO;
import com.devsuperior.bds01.servies.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController
{

	@Autowired
	private DepartmentService departmentService;


	@GetMapping
	public ResponseEntity<List<DepartamentDTO>> findAll()
	{
		List<DepartamentDTO> list = departmentService.findAll();

		return ResponseEntity.ok().body(list);
	}
}
