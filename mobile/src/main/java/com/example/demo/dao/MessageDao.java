package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.MessageQueue;
import com.example.demo.model.RealBusModel;

public interface MessageDao {
	List<MessageQueue> getMessage();
	List<RealBusModel> getBusByVehicleNo(@Param("_vehicle") String vehicle);
	void deleteByToken(@Param("_token")String token);
	void insertMessageQueue(@Param("_token") String token, @Param("_latitude") double latitude, 
			@Param("_longitude") double longitude, @Param("_vehicleno") String vehicle,
			@Param("_busstation") String busstation, @Param("_laststation") String laststation);
}
