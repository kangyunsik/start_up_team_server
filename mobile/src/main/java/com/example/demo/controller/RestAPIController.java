package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FacilityModel;
import com.example.demo.model.UserModel;
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
		List<UserModel> users = userService.printUserById("test1");
		System.out.println("users size : " + users.size());
		UserModel user = users.get(0);
		
		return "REST TEST!!";
	}

	@RequestMapping(value = "/locateupdate")
	public String locate(@RequestParam String x, @RequestParam String y, @RequestParam String id,
			String token) {
		System.out.println("x = " + x + " / y = " + y + " / id = " + id);
		userService.updateUserLocation(Double.parseDouble(x), Double.parseDouble(y), id,token);
		return "ok";
	}

	@RequestMapping(value = "/getRoute")
	public String[] getRouteMethod(@RequestParam String start_x, @RequestParam String start_y,
			@RequestParam String end_x, @RequestParam String end_y) {
		WebDriver driver = new WebDriver();
		driver.crawl(start_x, start_y, end_x, end_y);

		return driver.post;
	}

	@RequestMapping(value = "/setRoute")
	public Result setRouteMethod(@RequestParam String id, @RequestParam String busnum,
			@RequestParam String busstation) {
		Result result = null;
		List<FacilityModel> facilityModel = watchService.getLocationByName(busstation);
		FacilityModel facility = null;

	
		
		if (facilityModel.size() > 0)
			facility = facilityModel.get(0);

		List<FacilityModel> riding = null;
		UserModel user = userService.printUserById(id).get(0);

		
		riding = watchService.getRidingLocation(busnum, facility.getLatitude(), facility.getLongitude());

		FacilityModel f1, f2, target;
		f1 = riding.get(0);
		f2 = riding.get(1);

		double dist1 = dist(f1.getLatitude(), user.getLatitude(), f1.getLongitude(), user.getLongitude());
		double dist2 = dist(f2.getLatitude(), user.getLatitude(), f2.getLongitude(), user.getLongitude());

		target = dist1 > dist2 ? f2 : f1;

		if (riding.size() > 0)
			watchService.insertWatch(id, busnum, target.getLatitude(), target.getLongitude());

		result = Result.successInstance();

		return result;

	}

	double dist(double lat1, double lat2, double lng1, double lng2) {
		return (lat1 - lat2) * (lat1 - lat2) + (lng1 - lng2) * (lng1 - lng2);
	}

}
