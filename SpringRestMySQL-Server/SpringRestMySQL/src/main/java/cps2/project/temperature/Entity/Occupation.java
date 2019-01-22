package cps2.project.temperature.Entity;

import java.util.Date;

public class Occupation {

	private Long id;
	private String room;
	private String desrib;
	private Date start;
	private Date stop;
	


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getDesrib() {
		return desrib;
	}
	public void setDesrib(String desrib) {
		this.desrib = desrib;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStop() {
		return stop;
	}
	public void setStop(Date stop) {
		this.stop = stop;
	}

	
}
