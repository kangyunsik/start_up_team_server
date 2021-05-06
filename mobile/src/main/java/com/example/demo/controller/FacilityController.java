package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.calculator.Calculator;
import com.example.demo.model.FacilityModel;
import com.example.demo.service.FacilityService;

@Controller
public class FacilityController {
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	Calculator calculator;
	
	@RequestMapping("/facility")					// calculate
	public String location_recommend(Model model) {
		List<FacilityModel> m_facility = calculator.getPoint();
		
		
		
		model.addAttribute("facilities", m_facility);

		return "facility";
	}
	
	
	@RequestMapping("/facility_test")					// calculate
	public String getFacilityList(Model model) {
		List<FacilityModel> m_facility = facilityService.printFacility();
		model.addAttribute("facilities", m_facility);

		return "facility";
	}
}
