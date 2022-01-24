package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.CategoryDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.repositories.CategoryRepository;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
		return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id)
	{
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
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

	@Transactional()
	public CategoryDTO update(Long id, CategoryDTO dto)
	{
		try
		{
			Category entity = categoryRepository.getById(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	/**
	 * Sem transaction para capturar do banco de dados
	 * */
	public void delete(Long id)
	{
		try{

			categoryRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}catch (DataIntegrityViolationException e)
		{
			throw new DatabaseException("Integrity violation" + id);
		}
	}
}
