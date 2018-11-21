package com.javasampleapproach.springrest.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data")
public class Temperature {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "room")
	private String room;

	@Column(name = "value")
	private int value;


	public Temperature() {
	}

	public Temperature(String room, int value) {
		this.room = room;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setName(String room) {
		this.room = room;
	}

	public String getRoom() {
		return this.room;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getvalue() {
		return this.value;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", room=" + room + ", value=" + value + "]";
	}
}
