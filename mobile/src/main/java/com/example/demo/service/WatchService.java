package com.example.demo.service;

import java.util.List;

import com.example.demo.model.FacilityModel;

public interface WatchService {
	void insertWatch(String id, String busnum, double latitude, double longitude);
	List<FacilityModel> getLocationByName(String name);
	List<FacilityModel> getRidingLocation(String busnum, double latitude, double longitude);
}