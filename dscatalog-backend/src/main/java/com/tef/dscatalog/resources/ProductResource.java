package com.tef.dscatalog.resources;

import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.services.ProductServices;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductResource
{
	@Autowired
	private ProductServices productServices;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "ASC") String direction,
		@RequestParam(value = "orderBy", defaultValue = "name") String orderBy)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
			orderBy);
		Page<ProductDTO> products = productServices.findAllPaged(pageRequest);
		return ResponseEntity.ok(products);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id)

	{
		ProductDTO ProductDTO = productServices.findById(id);
		return ResponseEntity.ok(ProductDTO);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto)
	{
		dto = productServices.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto)
	{
		dto = productServices.update(id, dto);

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		productServices.delete(id);
		return ResponseEntity.noContent().build();

	}
}
