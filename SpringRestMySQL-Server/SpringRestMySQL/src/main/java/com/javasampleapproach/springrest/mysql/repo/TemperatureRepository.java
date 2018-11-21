package com.javasampleapproach.springrest.mysql.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.springrest.mysql.model.Temperature;


public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
	List<Temperature> findByRoom(String room);
	List<Temperature> findAll();
	
}