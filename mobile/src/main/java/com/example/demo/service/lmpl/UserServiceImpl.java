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
	public List<UserModel> getUserByToken(String token){
		return dao.getUserByToken(token);
	}

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
	public void updateUserLocation(double x,double y,String id) {
		dao.updateLocation(x,y,id);
		return;
	}

	@Override
	public List<UserModel> printUserById(String id) {
		return dao.getUserById(id);
	}
	
	@Override
	public void setToken(String token, String id) {
		dao.setToken(token, id);
		return;
	}
	
	@Override
	public void insertUser(String id,String pw,String email) {
		dao.insertUser(id,pw,email);
		return;
	}
	
}
