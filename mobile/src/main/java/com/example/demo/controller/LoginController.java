package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "/login")
	public Result login2(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String token = request.getParameter("token");
		Result result = Result.successInstance();

		UserModel loginUser = new UserModel(id, pw);
		
		if (verify(id, pw)) { // MANAGER.
			result.setData("OK");
			if(token != null) {
				userService.setToken(token, id);
			}else {
				System.out.println("token is null.");
			}
		}
		else
			result.setData("FAIL");
		//response.setHeader("Authorization", token);
		//result.setData(loginUser);

		return result;
	}

	public boolean verify(String id, String pw) {
		List<UserModel> m_user = userService.verifyUser(id,pw);
		if (m_user.size() > 0)
			return true;
		else
			return false;

	}
}
