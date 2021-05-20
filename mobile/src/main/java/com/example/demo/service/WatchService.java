package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BusTableModel;
import com.example.demo.model.FacilityModel;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;

public interface WatchService {
	void insertWatch(String id, String busnum, double latitude, double longitude);
	List<FacilityModel> getLocationByName(String name);
	List<FacilityModel> getRidingLocation(String busnum, double latitude, double longitude);
	void deleteExpired(int limit);
	List<BusTableModel> getBusId();
	void insertBusLocation(String vehicleno, String routenm, String latitude, String longitude);
	List<UserModel> findActUser();
	List<RealBusModel> getRealDist();
	void hit(String vehicleno);
	List<UserModel> getOverHitUser();
}