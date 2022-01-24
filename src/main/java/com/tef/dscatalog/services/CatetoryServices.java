package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.CategoryDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.repositories.CategoryRepository;
import com.tef.dscatalog.services.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id)
	{
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
		return new CategoryDTO(entity);
	}


	@Transactional()
	public CategoryDTO insert(CategoryDTO dto)
	{
		Category category = new Category();
		category.setName(dto.getName());
		category = categoryRepository.save(category);
		return new CategoryDTO(category);

	}
}
