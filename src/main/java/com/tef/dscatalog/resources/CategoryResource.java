package com.tef.dscatalog.resources;

import com.tef.dscatalog.dto.CategoryDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.services.CatetoryServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

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

	public ResponseEntity<Category> findById()
	{
		return ResponseEntity.ok(new Category(1L, "Books"));
	}
}
