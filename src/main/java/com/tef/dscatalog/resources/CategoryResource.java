package com.tef.dscatalog.resources;

import com.tef.dscatalog.dto.CategoryDTO;
import com.tef.dscatalog.services.CatetoryServices;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource
{
	@Autowired
	private CatetoryServices catetoryServices;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll()
	{
		List<CategoryDTO> categories = catetoryServices.findAll();

		return ResponseEntity.ok(categories);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id)

	{
		CategoryDTO categoryDTO = catetoryServices.findById(id);
		return ResponseEntity.ok(categoryDTO);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto)
	{
		dto = catetoryServices.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto)
	{
		dto = catetoryServices.update(id,dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		catetoryServices.delete(id);
		return ResponseEntity.noContent().build();

	}
}
