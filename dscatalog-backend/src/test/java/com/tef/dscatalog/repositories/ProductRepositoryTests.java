package com.tef.dscatalog.repositories;


import com.tef.dscatalog.entities.Product;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
class ProductRepositoryTests
{

	@Autowired
	private ProductRepository productRepository;

	@Test
	void deleteShouldDeleteObjectWhenIdExists()
	{
		long exintingId = 1L;
		productRepository.deleteById(1L);
		Optional<Product> result = productRepository.findById(1L);
		Assertions.assertTrue(result.isEmpty());
	}


	@Test
	void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(1000L);
		});
	}

}
