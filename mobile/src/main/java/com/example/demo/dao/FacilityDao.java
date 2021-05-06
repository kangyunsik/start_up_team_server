package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.FacilityModel;

@Mapper
public interface FacilityDao {
	List<FacilityModel> getFacility();
	List<FacilityModel> getBestFacility();
}
