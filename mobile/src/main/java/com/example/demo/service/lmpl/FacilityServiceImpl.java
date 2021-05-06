package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FacilityDao;
import com.example.demo.model.FacilityModel;
import com.example.demo.service.FacilityService;

@Service
public class FacilityServiceImpl implements FacilityService{
	@Autowired
	private FacilityDao dao;
	
	@Override
	public List<FacilityModel> printFacility(){
		List<FacilityModel> facility = dao.getFacility();
		return facility;
	}
	
	@Override
	public List<FacilityModel> printBestFacility(){
		List<FacilityModel> facility = dao.getBestFacility();
		return facility;
	}
}
