package com.tef.dscatalog.resources;

import com.tef.dscatalog.entities.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource
{

	@GetMapping

	public ResponseEntity<List<Category>> findAll()
	{
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		return ResponseEntity.ok(list);

	}
}
