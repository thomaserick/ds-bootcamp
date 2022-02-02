package com.devsuperior.bds01.repositories;

import com.devsuperior.bds01.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City,Long>
{
}
