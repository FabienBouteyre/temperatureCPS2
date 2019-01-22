package cps2.project.temperature.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cps2.project.temperature.Entity.Building;

@Repository
public interface RepBuilding extends JpaRepository<Building, Long> {

	@Query(value = "SELECT * FROM building WHERE floor = ?", nativeQuery = true)
	List<Building> findByFloor(Integer id);
	
	List<Building> findAll();
}
