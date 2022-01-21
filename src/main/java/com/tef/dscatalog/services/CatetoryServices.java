package com.tef.dscatalog.services;

import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatetoryServices
{
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll()
	{
		return categoryRepository.findAll();
	}
}
