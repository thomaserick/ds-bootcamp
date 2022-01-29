package com.tef.dscatalog.services;


import com.tef.dscatalog.repositories.ProductRepository;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests
{
	@InjectMocks
	private ProductServices productServices;

	@Mock
	private ProductRepository productRepository;

	private long existingId;
	private long nonExistinId;
	private long dependentId;

	@BeforeEach
	void setup()
	{
		existingId = 1L;
		nonExistinId = 1000L;
		dependentId = 4L;


		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository)
			.deleteById(nonExistinId);

		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository)
			.deleteById(dependentId);

	}

	@Test
	public void deleteShouldDoNothingWhenIdExists()
	{
		Assertions.assertDoesNotThrow(() -> productServices.delete(existingId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(ResourceNotFoundException.class,
			() -> productServices.delete(nonExistinId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistinId);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId()
	{
		Assertions.assertThrows(DatabaseException.class,
			() -> productServices.delete(dependentId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}



}
