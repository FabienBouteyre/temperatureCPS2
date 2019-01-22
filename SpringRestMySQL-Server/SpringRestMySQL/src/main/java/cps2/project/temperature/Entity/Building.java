package cps2.project.temperature.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "building")
public class Building {

	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @Column(name = "type")
	    private String type;

	    @Column(name = "floor")
	    private Integer floor;
	    
	    @Column(name = "room")
	    private String room;
	    
	    @Column(name = "geometryType")
	    private String geometryType;

	    @Column(name = "buildingGeometryId")
	    private Integer buildingGeometryId;
	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Integer getFloor() {
			return floor;
		}

		public void setFloor(Integer floor) {
			this.floor = floor;
		}

		public String getRoom() {
			return room;
		}

		public void setRoom(String room) {
			this.room = room;
		}

		public String getGeometryType() {
			return geometryType;
		}

		public void setGeometryType(String geometryType) {
			this.geometryType = geometryType;
		}

		public Integer getBuildingGeometryId() {
			return buildingGeometryId;
		}

		public void setBuildingGeometryId(Integer buildingGeometryId) {
			this.buildingGeometryId = buildingGeometryId;
		}
}
