package com.tef.dscatalog.resources;

import com.tef.dscatalog.dto.ClientDTO;
import com.tef.dscatalog.services.ClientServices;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource
{
	@Autowired
	private ClientServices clientServices;

	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(Pageable pageable)
	{
		Page<ClientDTO> clientDTOS = clientServices.findAllPaged(pageable);
		return ResponseEntity.ok(clientDTOS);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id)

	{
		ClientDTO clientsDTO = clientServices.findById(id);
		return ResponseEntity.ok(clientsDTO);
	}

	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto)
	{
		dto = clientServices.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto)
	{
		dto = clientServices.update(id, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		clientServices.delete(id);
		return ResponseEntity.noContent().build();

	}
}
