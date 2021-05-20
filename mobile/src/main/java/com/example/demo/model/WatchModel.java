package com.example.demo.model;

import java.sql.Timestamp;

public class WatchModel {
	private String id;
	private String routeno;
	private Timestamp registered_time;
	private double quit_latitude;
	private double quit_longitude;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteno() {
		return routeno;
	}
	public void setRouteno(String routeno) {
		this.routeno = routeno;
	}
	public double getQuit_latitude() {
		return quit_latitude;
	}
	public void setQuit_latitude(double quit_latitude) {
		this.quit_latitude = quit_latitude;
	}
	public double getQuit_longitude() {
		return quit_longitude;
	}
	public void setQuit_longitude(double quit_longitude) {
		this.quit_longitude = quit_longitude;
	}
	public Timestamp getRegistered_time() {
		return registered_time;
	}
	public void setRegistered_time(Timestamp registered_time) {
		this.registered_time = registered_time;
	}
	
}
