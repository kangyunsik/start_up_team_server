package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.WatchDao;
import com.example.demo.model.FacilityModel;
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

}
