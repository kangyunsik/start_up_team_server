package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FacilityModel;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.example.demo.util.Result;

@RestController
public class RestAPIController {

	@Autowired
	UserService userService;
	
	@Autowired
	WatchService watchService;
	
	@RequestMapping("/rest")
	public String restTest() { // @RequestParam String str
		return "REST TEST!!";
	}

	
	@RequestMapping(value = "/locateupdate")
	public String locate(@RequestParam String x, @RequestParam String y, @RequestParam String id) {
		System.out.println("x = " + x+" / y = " + y +" / id = " + id);
		userService.updateUserLocation(Double.parseDouble(x), Double.parseDouble(y), id);
		return "ok";
	}

	
	@RequestMapping(value = "/getRoute")
	public String[] getRouteMethod(@RequestParam String start_x, @RequestParam String start_y,
			@RequestParam String end_x, @RequestParam String end_y) {
		WebDriver driver = new WebDriver();
		driver.crawl(start_x,start_y,end_x,end_y);
		
		return driver.post;
	}
		
	@RequestMapping(value = "/setRoute")
	public Result setRouteMethod(@RequestParam String id, @RequestParam String busnum, 
			@RequestParam String busstation) {
		
		List<FacilityModel> facilityModel = watchService.getLocationByName(busstation);
		System.out.println("size : " +facilityModel.size());
		if(facilityModel.size() > 0)
			watchService.insertWatch(id,busnum,facilityModel.get(0).getLatitude(),facilityModel.get(0).getLongitude());
		
		
		Result result = Result.successInstance();
		return result;
	}
	
	
	
}
