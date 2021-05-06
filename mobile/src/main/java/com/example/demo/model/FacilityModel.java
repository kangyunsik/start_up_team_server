package com.example.demo.model;


public class FacilityModel {
	private String facility_name;
	private String location;		// 대략적 위치 (수도권 등)
	private double longitude;
	private double latitude;
	private String line;
	private double distance;
	private int time;
	
	public String getFacility_name() {
		return facility_name;
	}
	public String getLocation() {
		return location;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getLine() {
		return line;
	}
	public double getDistance() {
		return distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}	
}
