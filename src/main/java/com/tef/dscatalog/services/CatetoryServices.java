package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.CategoryDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.repositories.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatetoryServices
{
	@Autowired
	private CategoryRepository categoryRepository;

	//Não da lock no banco de dados
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll()
	{
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
	}
}
