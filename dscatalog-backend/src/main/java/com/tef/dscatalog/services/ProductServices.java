package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.entities.Product;
import com.tef.dscatalog.repositories.CategoryRepository;
import com.tef.dscatalog.repositories.ProductRepository;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServices
{
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	//Não da lock no banco de dados
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable)
	{
		Page<Product> list = productRepository.findAll(pageable);
		return list.map(product -> new ProductDTO(product, product.getCategories()));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id)
	{
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		return new ProductDTO(entity, entity.getCategories());
	}


	@Transactional()
	public ProductDTO insert(ProductDTO dto)
	{
		Product product = new Product();
		copyDtoToEntity(dto, product);
		product = productRepository.save(product);
		return new ProductDTO(product);

	}

	@Transactional()
	public ProductDTO update(Long id, ProductDTO dto)
	{
		try
		{
			Product entity = productRepository.getById(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	/**
	 * Sem transaction para capturar do banco de dados
	 */
	public void delete(Long id)
	{
		try
		{
			productRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DatabaseException("Integrity violation" + id);
		}
	}


	private void copyDtoToEntity(ProductDTO dto, Product product)
	{
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());

		//Limpar as categorias
		product.getCategories().clear();
		dto.getCategories().stream().forEach(categoryDTO -> {
			Category category = categoryRepository.getById(categoryDTO.getId());
			product.getCategories().add(category);
		});

	}


}
