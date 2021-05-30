package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FacilityModel;
import com.example.demo.model.MessageQueue;
import com.example.demo.model.UserModel;
import com.example.demo.service.MessageQueueService;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.example.demo.util.FcmUtil;
import com.example.demo.util.Result;

@RestController
public class RestAPIController {

	@Autowired
	UserService userService;

	@Autowired
	WatchService watchService;
	
	@Autowired
	MessageQueueService messageQueueService;
	
	@Autowired
	FcmUtil fcmUtil;
	
	@RequestMapping("/signin")
	public String signin(@RequestParam String id, @RequestParam String pw, @RequestParam String email) {
		System.out.println("id : " + id + " / pw = " + pw + " email = " + email);
		userService.insertUser(id,pw,email);
		String result = "OK";
		return result;
	}
	
	
	@RequestMapping("/clearwatchtable")
	public String clear() { // @RequestParam String str
		watchService.clearWatchTable();
	
		return "OK";
	}
	
	@RequestMapping("/clearmessagequeue")
	public String clearmq() { // @RequestParam String str
		watchService.clearMessageQueue();
	
		return "OK";
	}

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
		System.out.println("x = " + x + " / y = " + y + " / id = " + id + " / token : " + token);
		userService.updateUserLocation(Double.parseDouble(x), Double.parseDouble(y), id);
		
		for(MessageQueue msg : messageQueueService.getMessage()) {
			List<UserModel> uml = userService.getUserByToken(msg.getToken());
			for(UserModel um : uml) {
				if(um.getId().equals(id)) {
					System.out.println("send stop");
					return "stop";
				}
			}
		}
		
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
			@RequestParam String busstation, @RequestParam String startstation, @RequestParam String laststation) {
		Result result = null;
		System.out.println("id : " + id + " / busnum : " + busnum + " / busstation = " + busstation +" / start : " + startstation);
		System.out.println("last : " + laststation);
		List<FacilityModel> facilityModel = watchService.getLocationByName(busstation);
		FacilityModel end = null;

		Location loc = null;
		
		List<FacilityModel> ss = watchService.getLocationByName(startstation);
		
		if(ss.size()>0) {
			FacilityModel start = ss.get(0);
			
			loc = new Location(start.getNodename(),start.getLatitude(),start.getLongitude());
			System.out.println(start.getNodename() +":" +start.getLatitude()+":"+start.getLongitude());
		}else {
			System.out.println("ss size is 0. error.");
		}
		
		if (facilityModel.size() > 0)
			end = facilityModel.get(0);

		//UserModel user = userService.printUserById(id).get(0);

		


		watchService.insertWatch(id, busnum, end.getLatitude(), end.getLongitude());
		watchService.setStations(id,busstation,laststation);
		
		loc.dname = end.getNodename();
		loc.dlatitude = end.getLatitude();
		loc.dlongitude = end.getLongitude();
		
		result = Result.successInstance();
		result.setData(loc);
		return result;

	}

	double dist(double lat1, double lat2, double lng1, double lng2) {
		return (lat1 - lat2) * (lat1 - lat2) + (lng1 - lng2) * (lng1 - lng2);
	}

	class Location{
		public String name;
		public double latitude;
		public double longitude;
		public String dname;
		public double dlatitude;
		public double dlongitude;
		public Location(String name, double latitude, double longitude) {
			this.name = name;
			this.latitude = latitude;
			this.longitude = longitude;
		}
	}
}
