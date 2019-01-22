package cps2.project.temperature.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps2.project.temperature.Entity.BuildingGeometry;
import cps2.project.temperature.Repository.RepBuildingGeometry;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class BuildingGeometryRestController {

	@Autowired
	private RepBuildingGeometry repBuildingGeometry;

	@GetMapping("/buildingGeometry")
	public List<BuildingGeometry> GetBuildingGeometryList() {
		return repBuildingGeometry.findAll();
	}
	
	@GetMapping("/buildingGeometryById")
	public List<BuildingGeometry> GetBuildingGeometryListByBuildingId(@RequestParam Integer id) {
		return repBuildingGeometry.findByBuildingId(id);
	}
}
