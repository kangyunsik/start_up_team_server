package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	private String ident;
	private String hashed_pw;
	private double longitude;
	private double latitude;
	
	public UserModel(String id, String pw) {
		this.ident = id;
		this.hashed_pw = pw;
	}
	
	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getHashed_pw() {
		return hashed_pw;
	}

	public void setHashed_pw(String hashed_pw) {
		this.hashed_pw = hashed_pw;
	}
	
}
