package cps2.project.temperature.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "advice")
public class Advice {

	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @Column(name = "type")
	    private String type;

	    @Column(name = "description")
	    private String description;
	    
	    @Column(name = "date")
	    private Date date;
	    
	    @Column(name = "floor")
	    private String floor;
	    
	    @Column(name = "room")
	    private String room;
	    
	    @Column(name = "light")
	    private String light;

	    @Column(name = "roomTemperature")
	    private String roomTemperature;
	    
	    @Column(name = "outsideTemperature")
	    private String outsideTemperature;

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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getFloor() {
			return floor;
		}

		public void setFloor(String floor) {
			this.floor = floor;
		}

		public String getRoom() {
			return room;
		}

		public void setRoom(String room) {
			this.room = room;
		}

		public String getLight() {
			return light;
		}

		public void setLight(String light) {
			this.light = light;
		}

		public String getRoomTemperature() {
			return roomTemperature;
		}

		public void setRoomTemperature(String roomTemperature) {
			this.roomTemperature = roomTemperature;
		}

		public String getOutsideTemperature() {
			return outsideTemperature;
		}

		public void setOutsideTemperature(String outsideTemperature) {
			this.outsideTemperature = outsideTemperature;
		}

}
