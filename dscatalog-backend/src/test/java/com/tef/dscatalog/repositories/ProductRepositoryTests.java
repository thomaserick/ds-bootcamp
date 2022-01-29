package com.tef.dscatalog.repositories;


import com.tef.dscatalog.Factory;
import com.tef.dscatalog.entities.Product;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
class ProductRepositoryTests
{

	@Autowired
	private ProductRepository productRepository;


	private long exintingId;
	private long nonExintingId;
	private long countTotalProducsts;

	@BeforeEach
	void setup()
	{
		exintingId = 1L;
		nonExintingId = 1000L;
		countTotalProducsts = 25L;

	}

	@Test
	void deleteShouldDeleteObjectWhenIdExists()
	{
		productRepository.deleteById(exintingId);
		Optional<Product> result = productRepository.findById(exintingId);
		Assertions.assertTrue(result.isEmpty());
	}


	@Test
	void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(nonExintingId);
		});
	}

	@Test
	void saveShouldPersistWithAutoIncrementWhenIdIsNull()
	{
		Product product = Factory.createProduct();
		product.setId(null);

		product = productRepository.save(product);

		Assertions.assertNotNull(product);
		Assertions.assertEquals(countTotalProducsts + 1, product.getId());
	}

	@Test
	void findByIdShouldReturnProductNotIsEmptyWhenIdExist()
	{
		Optional<Product> product = productRepository.findById(exintingId);
		Assertions.assertTrue(product.isPresent());
	}

	@Test
	void findByIdShouldReturnProductNotIsEmptyWhenIdDoesNotExist()
	{
		Optional<Product> product = productRepository.findById(nonExintingId);
		Assertions.assertTrue(product.isEmpty());
	}

}
