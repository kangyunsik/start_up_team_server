package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.util.FcmUtil;

@Controller
public class FCM_Controller{
	
	@Autowired
	UserService userService;

	@Autowired
	FcmUtil fcmUtil;
	
	
	@RequestMapping(value = "/fcmtest.do")
	public @ResponseBody String fcmtest(HttpServletRequest request,HttpServletResponse response, Model model)
	throws Exception{
		String testid="test3";
		
		
		//String tokenId="dfd7zSSpSCqyAgV8Wlpsr1:APA91bGsmCqWiVFVvu-FepRkoYMiZDX7PDqi2VRBkQ4neFvZJ3izxlDEWy0V2Uw9d7MbcIow_LNxirypNLs4qRYJS8EpRml_-FSIkZ4vqEWL1gL37UPhnwwNZx7k09bnm_kQAu0gDreC";
		List<UserModel> users = userService.printUser();
		for(UserModel user : users) {
			if(user.getId().equals(testid)) {
				String title="제목입니다";
				String content="내용입니다";
				
				fcmUtil.send_FCM(user.getToken(), title, content);
			}
		}
		
		
		return "complete.";
	}
}
