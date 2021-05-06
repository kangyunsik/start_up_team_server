package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
public class RestAPIController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/rest")
	public String restTest() { // @RequestParam String str
		return "REST TEST!!";
	}
	
	/*
	 * @PostMapping(value = "/locate") public ArrayList<Route>
	 * locate(HttpServletRequest request, Model model, HttpServletResponse response)
	 * { //ArrayList<Bus> busList; //ArrayList<Route> routeList;
	 * 
	 * String user_x = request.getParameter("user_x"); String user_y =
	 * request.getParameter("user_y"); String start_x =
	 * request.getParameter("start_x"); String start_y =
	 * request.getParameter("start_y"); String end_x =
	 * request.getParameter("end_x"); String end_y = request.getParameter("end_y");
	 * 
	 * System.out.println("user\tx: " + user_x+"\ty: "+user_y);
	 * System.out.println("start\tx: " + start_x+"\ty: "+start_y);
	 * System.out.println("end\tx: " + end_x+"\ty: "+end_y);
	 * 
	 * return null; }
	 */
	
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
		
	/*
	 * public ArrayList<Route> findWay(String a, String b, String c, String d) {
	 * return null; } public ArrayList<Bus>parseFromRoute(ArrayList<Route>
	 * routeList) { return null;
	 */
	
	@RequestMapping("/rest2")
	public String restTest(@RequestParam String str) { // @RequestParam String str
		return str + "REST TEST!!";
	}
	
}
