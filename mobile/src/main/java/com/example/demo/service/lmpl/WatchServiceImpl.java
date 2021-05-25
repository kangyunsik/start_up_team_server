package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.WatchDao;
import com.example.demo.model.BusTableModel;
import com.example.demo.model.FacilityModel;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.WatchService;

@Service
public class WatchServiceImpl implements WatchService{

	@Autowired
	private WatchDao dao;
	
	@Override
	public void insertWatch(String id, String busnum, double latitude, double longitude) {
		dao.insertWatch(id, busnum, latitude, longitude);
		return;
	}

	@Override
	public List<FacilityModel> getLocationByName(String name) {
		return dao.getLocationByName(name);
	}

	@Override
	public List<FacilityModel> getRidingLocation(String name, double latitude, double longitude) {
		return dao.getRidingLocation(name,latitude,longitude);
	}

	@Override
	public void deleteExpired(int limit) {
		dao.deleteExpired(limit);
		return;
	}

	@Override
	public List<BusTableModel> getBusId(String citycode) {
		return dao.getBusId(citycode);
	}

	@Override
	public void insertBusLocation(String vehicleno, String routenm, String latitude, String longitude) {
		dao.insertBusLocation(vehicleno,routenm,Double.parseDouble(latitude),Double.parseDouble(longitude));
		return;
	}
	
	public List<UserModel> findActUser(){
		return dao.findActUser();
	}
	
	public List<RealBusModel> getRealDist(){
		return dao.getRealDist();
	}
	
	@Override
	public void hit(String vehicleno) {
		dao.hit(vehicleno);
		return;
	}

	@Override
	public List<UserModel> getOverHitUser() {
		return dao.getOverHitUser();
	}

	@Override
	public void deleteByToken(String token) {
		dao.deleteByToken(token);
		return;
	}

	@Override
	public void clearWatchTable() {
		dao.clearWatchTable();
		return;
	}
}
