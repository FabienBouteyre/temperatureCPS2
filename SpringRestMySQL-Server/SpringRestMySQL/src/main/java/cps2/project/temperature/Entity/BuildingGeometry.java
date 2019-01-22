package cps2.project.temperature.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "building_geometry")
public class BuildingGeometry {

	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @Column(name = "buildingId")
	    private Integer buildingId;

	    @Column(name = "pointId")
	    private Integer pointId;
	    
	    @Column(name = "x")
	    private Integer x;
	    
	    @Column(name = "y")
	    private Integer y;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(Integer buildingId) {
			this.buildingId = buildingId;
		}

		public Integer getPointId() {
			return pointId;
		}

		public void setPointId(Integer pointId) {
			this.pointId = pointId;
		}

		public Integer getX() {
			return x;
		}

		public void setX(Integer x) {
			this.x = x;
		}

		public Integer getY() {
			return y;
		}

		public void setY(Integer y) {
			this.y = y;
		}	    
}
