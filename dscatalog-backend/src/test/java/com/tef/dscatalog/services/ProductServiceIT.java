package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.repositories.ProductRepository;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

//Carrega o contexto da aplicação
//Test transactional e faz roolback do banco
@SpringBootTest
@Transactional
public class ProductServiceIT
{

	@Autowired
	private ProductServices productServices;

	@Autowired
	private ProductRepository productRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;


	@BeforeEach
	void setup()
	{
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}


	@Test
	void deleteShouldDeleteResourceWhenIdExists()
	{
		productServices.delete(existingId);
		Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
	}

	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(ResourceNotFoundException.class,
			() -> productServices.delete(nonExistingId));
	}

	@Test
	void findAllPagedShouldReturnWhenPage0Size10()
	{
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProductDTO> result = productServices.findAllPaged(pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist()
	{
		PageRequest pageRequest = PageRequest.of(50, 10);
		Page<ProductDTO> result = productServices.findAllPaged(pageRequest);

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void findAllPagedShouldReturnSortedPagedWhenSortByName()
	{

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		Page<ProductDTO> result = productServices.findAllPaged(pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}

}
