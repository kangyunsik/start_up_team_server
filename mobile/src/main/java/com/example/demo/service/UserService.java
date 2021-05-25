package com.example.demo.service;

import java.util.List;
import com.example.demo.model.UserModel;

public interface UserService {
	List<UserModel> printUser();
	List<UserModel> printUserById(String id);
	List<UserModel> verifyUser(String id, String pw);
	void updateUserLocation(double x,double y,String id,String token);
	List<UserModel> getUserByToken(String token);
}