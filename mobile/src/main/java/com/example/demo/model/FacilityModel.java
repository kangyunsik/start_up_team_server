package com.example.demo.model;


public class FacilityModel {
	private String facility_name;
	private String routeid;
	private String nodeid;
	private String nodename;
	private int ord;
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
	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}	
}
