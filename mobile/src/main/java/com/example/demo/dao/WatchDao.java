package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.BusTableModel;
import com.example.demo.model.FacilityModel;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WatchModel;

@Mapper
public interface WatchDao {
	void insertWatch(@Param("_id") String id, @Param("_busnum") String busnum, 
			@Param("_latitude") double latitude,@Param("_longitude") double longitude);
	List<FacilityModel> getLocationByName(@Param("_name") String name);
	List<FacilityModel> getRidingLocation(@Param("_name") String name, @Param("_latitude") double latitude,
			@Param("_longitude") double longitude);
	void deleteExpired(@Param("_limit") int limit);
	List<BusTableModel> getBusId(@Param("_citycode") String citycode);
	void insertBusLocation(@Param("_vehicleno")String vehicleno, @Param("_routeno") String routenm,
			@Param("_latitude") double latitude, @Param("_longitude") double longitude);
	List<UserModel> findActUser();
	List<RealBusModel> getRealDist();
	void hit(@Param("_vehicleno") String vehicleno);
	List<UserModel> getOverHitUser();
	void deleteByToken(@Param("_token") String token);
	void clearWatchTable();
	void updateHit(@Param("_vehicleno") String vehicleno);
	void setStations(@Param("_id")String id,@Param("_busstation") String busstation, @Param("_laststation") String laststation);
	List<WatchModel> getWatchById(@Param("_id")String id);
}
