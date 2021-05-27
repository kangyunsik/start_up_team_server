package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MessageQueue;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;

public interface MessageQueueService {
	List<MessageQueue> getMessage();
	List<RealBusModel> getBusByVehicleNo(String vehicle);
	void deleteByToken(String token);
	void insertMessageQueue(String token, double latitude, double longitude, String vehicle,
			String busstation, String laststation);
	List<UserModel> findActUser();
}
