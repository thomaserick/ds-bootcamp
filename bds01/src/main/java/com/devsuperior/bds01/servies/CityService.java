package com.devsuperior.bds01.servies;

import com.devsuperior.bds01.dto.CityDTO;
import com.devsuperior.bds01.entities.City;
import com.devsuperior.bds01.repositories.CityRepository;
import com.devsuperior.bds01.servies.exception.DatabaseException;
import com.devsuperior.bds01.servies.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService
{

	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll()
	{
		List<City> list = cityRepository.findAll(Sort.by("name"));
		return list.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
	}


	@Transactional
	public CityDTO insert(CityDTO cityDTO)
	{
		City city = fromDTO(cityDTO);
		city = cityRepository.save(city);
		return new CityDTO(city);
	}

	private City fromDTO(CityDTO cityDTO)
	{
		return new City(null, cityDTO.getName());
	}

	public void delete(Long id)
	{
		try
		{
			cityRepository.deleteById(id);
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
}
