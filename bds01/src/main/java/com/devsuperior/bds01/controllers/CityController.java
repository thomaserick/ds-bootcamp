package com.devsuperior.bds01.controllers;


import com.devsuperior.bds01.dto.CityDTO;
import com.devsuperior.bds01.servies.CityService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/cities")
public class CityController
{

	@Autowired
	private CityService cityService;

	@PostMapping
	public ResponseEntity<CityDTO> insert(@RequestBody CityDTO cityDTO)
	{
		cityDTO = cityService.insert(cityDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(cityDTO.getId()).toUri();

		return ResponseEntity.created(uri).body(cityDTO);

	}

	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll()
	{
		List<CityDTO> list = cityService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		cityService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
