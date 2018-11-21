package com.javasampleapproach.springrest.mysql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.springrest.mysql.model.Temperature;
import com.javasampleapproach.springrest.mysql.repo.TemperatureRepository;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TemperatureController {
	
	@Autowired
	TemperatureRepository repository;
	
	@GetMapping("/temperature")
	public List<Temperature> getAllTemperatures() {
		System.out.println("Get all Temperatures...");

		List<Temperature> temperatures = new ArrayList<>();
		repository.findAll().forEach(temperatures::add);

		return temperatures;
	}
}