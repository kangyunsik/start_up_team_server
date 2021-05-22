package com.example.demo.model;

public class MessageQueue {
	private String token;
	private double latitude;
	private double longitude;
	private String vehicleno;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
}
