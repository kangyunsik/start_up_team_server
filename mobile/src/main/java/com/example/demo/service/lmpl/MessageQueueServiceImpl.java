package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MessageDao;
import com.example.demo.model.MessageQueue;
import com.example.demo.model.RealBusModel;
import com.example.demo.service.MessageQueueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageQueueServiceImpl implements MessageQueueService {

	@Autowired
	MessageDao dao;
	
	@Override
	public List<MessageQueue> getMessage() {
		// TODO Auto-generated method stub
		return dao.getMessage();
	}

	@Override
	public List<RealBusModel> getBusByVehicleNo(String vehicle) {
		// TODO Auto-generated method stub
		return dao.getBusByVehicleNo(vehicle);
	}

	@Override
	public void deleteByToken(String token) {
		// TODO Auto-generated method stub
		dao.deleteByToken(token);
		return;
	}

	@Override
	public void insertMessageQueue(String token, double latitude, double longitude, String vehicle) {
		dao.insertMessageQueue(token, latitude, longitude, vehicle);
		return;
	}
}
