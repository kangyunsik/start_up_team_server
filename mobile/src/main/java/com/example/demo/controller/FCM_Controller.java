package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.util.FcmUtil;

@Controller
public class FCM_Controller{

	@Autowired
	FcmUtil fcmUtil;
	
	
	@RequestMapping(value = "/fcmtest.do")
	public @ResponseBody String fcmtest(HttpServletRequest request,HttpServletResponse response, Model model)
	throws Exception{
		
		//String tokenId="dfd7zSSpSCqyAgV8Wlpsr1:APA91bGsmCqWiVFVvu-FepRkoYMiZDX7PDqi2VRBkQ4neFvZJ3izxlDEWy0V2Uw9d7MbcIow_LNxirypNLs4qRYJS8EpRml_-FSIkZ4vqEWL1gL37UPhnwwNZx7k09bnm_kQAu0gDreC";
		String tokenId="eJqfod0wT5GAyX_eiFiUHM:APA91bGplH4J1SUZesv2FaQk_h2qybKHH2kVoDAI9on0VT5hn3rDR_5I7WDPwJNZl_ula7JXdFQrEGkVV9Gez11PNSkWeY295IqhftkOIE-MwA-VdQf_DTeBZBhZCYlSKgV_cUzGF-70";
		String title="제목입니다";
		String content="내용입니다";
		
		fcmUtil.send_FCM(tokenId, title, content);
		
		return "test";
	}
}
