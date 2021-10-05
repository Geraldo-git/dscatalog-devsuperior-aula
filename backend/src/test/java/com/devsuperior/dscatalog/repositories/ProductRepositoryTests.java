package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;

	@Autowired
	private ProductRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
		Product product = Factory.createProduct();
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingId);
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExits() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});

	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhwnIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}

	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertNotNull(result);
	}

	@Test
	public void findByIdShouldNoReturnObjectWhenIdNoExists() {
		Optional<Product> result = repository.findById(nonExistingId);
		Assertions.assertFalse(result.isPresent());
	}

}