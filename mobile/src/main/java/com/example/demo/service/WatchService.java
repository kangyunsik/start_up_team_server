package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BusTableModel;
import com.example.demo.model.FacilityModel;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WatchModel;

public interface WatchService {
	void insertWatch(String id, String busnum, double latitude, double longitude);
	List<FacilityModel> getLocationByName(String name);
	List<FacilityModel> getRidingLocation(String busnum, double latitude, double longitude);
	void deleteExpired(int limit);
	List<BusTableModel> getBusId(String citycode);
	void insertBusLocation(String vehicleno, String routenm, String latitude, String longitude);
	List<UserModel> findActUser();
	List<RealBusModel> getRealDist();
	void hit(String vehicleno);
	List<UserModel> getOverHitUser();
	void deleteByToken(String token);
	void clearWatchTable();
	void updateHit(String vehicleno);
	void setStations(String id, String busstation, String laststation);
	List<WatchModel> getWatchById(String id);
}