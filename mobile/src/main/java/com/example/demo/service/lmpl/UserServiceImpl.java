package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao dao;
	
	@Override
	public List<UserModel> printUser(){
		List<UserModel> user = dao.getUser();
		return user;
	}

	@Override
	public List<UserModel> verifyUser(String id, String pw) {
		List<UserModel> user = dao.targetUser(id, pw);
		return user;
	}

	@Override
	public void updateUserLocation(double x,double y,String id, String token) {
		dao.updateLocation(x,y,id,token);
		return;
	}

	@Override
	public List<UserModel> printUserById(String id) {
		return dao.getUserById(id);
	}
	
}
