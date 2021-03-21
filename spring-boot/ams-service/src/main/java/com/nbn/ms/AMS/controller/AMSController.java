package com.nbn.ms.AMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbn.ms.AMS.model.AMSRequestDto;
import com.nbn.ms.AMS.model.AMSResponseDto;
import com.nbn.ms.AMS.service.AMSService;


@RestController
@RequestMapping("/AMS/v1")
public class AMSController {

	//@Autowired
	//WebClient.Builder webClientBuilder;

	@Autowired
	private AMSService amsService;

	
	@PostMapping(path= "/externalConfig", consumes = "application/json", produces = "application/json")
	public AMSResponseDto externalConfig(@RequestBody AMSRequestDto incident) {
		System.out.println("Inside createIncident() method ");
		return amsService.externalConfig(incident);

	}
	
	
	@PostMapping(path= "/incidents", consumes = "application/json", produces = "application/json")
	public AMSResponseDto createIncident(@RequestBody AMSRequestDto incident) {
		System.out.println("Inside createIncident() method ");
		return amsService.postIncident(incident);

	}



}


