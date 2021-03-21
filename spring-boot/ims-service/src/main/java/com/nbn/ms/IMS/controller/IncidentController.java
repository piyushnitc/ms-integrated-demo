package com.nbn.ms.IMS.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nbn.ms.IMS.entity.Incident;
import com.nbn.ms.IMS.exception.IncidentNotFoundException;
import com.nbn.ms.IMS.model.IncidentDAOService;
import com.nbn.ms.IMS.tools.NRLogService;


@RestController
@RequestMapping("/IMS/v1")
public class IncidentController {
    
	
	@Autowired
	private NRLogService nrLogService;
	
	@Autowired
	private IncidentDAOService incidentDAOService;

	@PostMapping(path= "/incidents", consumes = "application/json", produces = "application/json")
	public Incident createIncident(@RequestBody Incident incident) {
		
		System.out.println("inside createIncident() method");
		System.out.println(incident.toString());
		
		Incident savedIncident = incidentDAOService.save(incident);
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				//.buildAndExpand(savedIncident.getId()).toUri();
		nrLogService.info("New incident is created");
		System.out.println("completed createIncident() method ");
		return savedIncident;
	}

	@DeleteMapping("/incidents/{id}")
	public void deleteIncident(@PathVariable int id) {
		Incident incident = incidentDAOService.deleteById(id);
		if (incident == null) {
			throw new IncidentNotFoundException("Id = " + id);
		}
		nrLogService.info("Incident [" + String.valueOf(id) + "] deleted");

	}

	@PutMapping("/incidents/{id}")
	public void updateIncident(@RequestBody Incident incident, @PathVariable int id) {

		Incident UpdatedIncident = incidentDAOService.update(id, incident);

		if (UpdatedIncident == null) {
			throw new IncidentNotFoundException("Id = " + id);
		}
		nrLogService.info("Incident [" + String.valueOf(id) + "] updated");

	}

	
	@GetMapping("/incidents")
	public List<Incident> retrieveAllIncidents() {
		nrLogService.info("Fetching all incidents");
		return incidentDAOService.findAll();
	}

	@GetMapping("/incidents/{id}")
	public Incident retrieveIncident(@PathVariable int id) {
		Incident incident = incidentDAOService.findById(id);
		if (incident == null) {
			throw new IncidentNotFoundException("Id = " + id);
		}
		nrLogService.info("Incident [" + String.valueOf(id) + "] is retrieved");
		return incident;
	}


	
}
