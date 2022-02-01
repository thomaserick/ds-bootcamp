package com.devsuperior.bds01.controllers;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.servies.EmployeeService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/employees")
public class EmployeeController
{

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable)
	{
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
			Sort.by("name"));
		Page<EmployeeDTO> list = employeeService.findAll(pageRequest);
		return ResponseEntity.ok().body(list);

	}

	@PostMapping
	public ResponseEntity<EmployeeDTO> insert(@RequestBody EmployeeDTO dto)
	{
		EmployeeDTO employeeDTO = employeeService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(employeeDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(employeeDTO);
	}
}
