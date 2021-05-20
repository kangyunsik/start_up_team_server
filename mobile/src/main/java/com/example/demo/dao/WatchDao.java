package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.FacilityModel;

@Mapper
public interface WatchDao {
	void insertWatch(@Param("_id") String id, @Param("_busnum") String busnum, 
			@Param("_latitude") double latitude,@Param("_longitude") double longitude);
	List<FacilityModel> getLocationByName(@Param("_name") String name);
	List<FacilityModel> getRidingLocation(@Param("_busnum") String busnum, @Param("_latitude") double latitude,
			@Param("_longitude") double longitude);
}
