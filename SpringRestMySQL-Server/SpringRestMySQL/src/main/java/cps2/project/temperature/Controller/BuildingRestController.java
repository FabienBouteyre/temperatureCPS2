package cps2.project.temperature.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps2.project.temperature.Entity.Building;
import cps2.project.temperature.Repository.RepBuilding;


@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class BuildingRestController {

	@Autowired
	private RepBuilding repBuilding;

	@GetMapping("/building")
	public List<Building> GetBuildingList() {
		return repBuilding.findAll();
	}
	
	@GetMapping("/buildingByFloor")
	public List<Building> GetBuildingByFloor(@RequestParam Integer floor) {
		return repBuilding.findByFloor(floor);
	}
}
