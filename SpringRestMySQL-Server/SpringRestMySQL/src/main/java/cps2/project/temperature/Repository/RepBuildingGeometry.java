package cps2.project.temperature.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cps2.project.temperature.Entity.BuildingGeometry;

@Repository
public interface RepBuildingGeometry extends JpaRepository<BuildingGeometry, Long> {

	@Query(value = "SELECT * FROM building_geometry WHERE building_id = ? ORDER BY point_id ASC", nativeQuery = true)
	List<BuildingGeometry> findByBuildingId(Integer id);
	
	List<BuildingGeometry> findAll();
}
