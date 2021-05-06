package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.google.gson.JsonObject;


@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/userlist")					// userlist 반환.
	public String list(Model model) {
		List<UserModel> m_user = userService.printUser();
		model.addAttribute("userList", m_user);

		return "userlist";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/jsontest")
    public String test1(){

		JsonObject obj = new JsonObject();

		obj.addProperty("title", "테스트3");
	    obj.addProperty("content", "테스트3 내용");

	    JsonObject data = new JsonObject();

	    data.addProperty("time", "12:00");

	    obj.add("data", data);
        
        return obj.toString();

    }
}
