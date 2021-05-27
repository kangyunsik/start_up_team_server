package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.UserModel;

@Mapper
public interface UserDao {
	List<UserModel> getUser();

	List<UserModel> targetUser(@Param("_id") String id, @Param("_pw") String pw);
	void updateLocation(@Param("_x") double x, @Param("_y") double y,@Param("_id") String id);
	
	List<UserModel> getUserById(@Param("_id") String id);
	List<UserModel> getUserByToken(@Param("_token") String token);
	void setToken(@Param("_token") String token, @Param("_id") String id);
	void insertUser(@Param("_id") String id, @Param("_pw") String pw, @Param("_email") String email);
}
