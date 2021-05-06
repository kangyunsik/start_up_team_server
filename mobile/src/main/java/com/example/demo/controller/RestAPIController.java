package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController {

	@RequestMapping("/rest")
	public String restTest() { // @RequestParam String str
		return "REST TEST!!";
	}
	
	@PostMapping(value = "/locate")
	public ArrayList<Route> locate(HttpServletRequest request, Model model, HttpServletResponse response) {
		//ArrayList<Bus> busList;
		//ArrayList<Route> routeList;
		
		String user_x = request.getParameter("user_x");
		String user_y = request.getParameter("user_y");
		String start_x = request.getParameter("start_x");
		String start_y = request.getParameter("start_y");
		String end_x = request.getParameter("end_x");
		String end_y = request.getParameter("end_y");
		
		System.out.println("user\tx: " + user_x+"\ty: "+user_y);
		System.out.println("start\tx: " + start_x+"\ty: "+start_y);
		System.out.println("end\tx: " + end_x+"\ty: "+end_y);
		
		//routeList = findWay(start_x,start_y,end_x,end_y);
		//busList = parseFromRoute(routeList);
		
		// Required : Calculating Thread Start.	Calculate(busList);
		//						[Asynchronized]
		//						@Autowired
		//						private EstimateService estimateService;
		// Required : Alarm to User.
		
		return null;
	}
	
	@RequestMapping(value = "/locateupdate")
	public String locate(@RequestParam String x, @RequestParam String y) {
		System.out.println("x = " + x+" / y = " + y);	
		return "ok";
	}

	
	@RequestMapping(value = "/getRoute")
	public String[] getRouteMethod() {
		//Route rt = new Route();
		WebDriver driver = new WebDriver();
		driver.crawl();
		
		
		
		//String[] s ="1??39<total> 1????(?1?)<left> 4?<time> 0<busnum> 0<station> 17?<time> 643<busnum> ?????<station> 6?<time> 0<busnum> 0<station> 45?<time> 148<busnum> ????????<station> 24?<time> 173<busnum> ??????<station> 3?<time> 0<busnum> 0<station> ????<last>1??39<total> 6????(?10?)<left> 4?<time> 0<busnum> 0<station> 17?<time> 5524<busnum> ?????<station> 6?<time> 0<busnum> 0<station> 45?<time> 148<busnum> ????????<station> 24?<time> 173<busnum> ??????<station> 3?<time> 0<busnum> 0<station> ????<last>1??45<total> 1????(?1?)<left> 4?<time> 0<busnum> 0<station> 17?<time> 643<busnum> ?????<station> 6?<time> 0<busnum> 0<station> 51?<time> 148<busnum> ????????<station> 5?<time> 0<busnum> 0<station> 18?<time> 1137<busnum> ???????????<station> 4?<time> 0<busnum> 0<station> ?????<last>1??45<total> 6????(?10?)<left> 4?<time> 0<busnum> 0<station> 17?<time> 5524<busnum> ?????<station> 6?<time> 0<busnum> 0<station> 51?<time> 148<busnum> ????????<station> 5?<time> 0<busnum> 0<station> 18?<time> 1137<busnum> ???????????<station> 4?<time> 0<busnum> 0<station> ?????<last>1??46<total> 6????(?10?)<left> 4?<time> 0<busnum> 0<station> 17?<time> 5524<busnum> ?????<station> 6?<time> 0<busnum> 0<station> 51?<time> 148<busnum> ????????<station> 7?<time> 0<busnum> 0<station> 17?<time> 1017<busnum> ????<station> 4?<time> 0<busnum> 0<station> ?????<last>".split(" ");
		return driver.post;
	}
		
	class Route{
		String rtnm;
		ArrayList<String> busInfo;
		ArrayList<String> timeInfo;
		public Route() {
			this.rtnm = "1";
			busInfo = new ArrayList<String>();
			timeInfo = new ArrayList<String>();
			busInfo.add("1BUS");
			busInfo.add("walk");
			busInfo.add("3BUS");
			timeInfo.add("20min");
			timeInfo.add("3min");
			timeInfo.add("15min");
		}
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
