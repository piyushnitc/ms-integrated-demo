package com.nbn.ms.IMS.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.nbn.ms.IMS.entity.Incident;



@Component
public class IncidentDAOService {
	private static List<Incident> incidents = new ArrayList<>();

	private static int incidentsCount = 3;
	private static int currentIndex = 0;

	static {
		incidents.add(new Incident(1, new Date(), "Test1", "Description", "NewIncident"));
		incidents.add(new Incident(2, new Date(), "Test2", "Description", "InProgress"));
		incidents.add(new Incident(3, new Date(), "Test3", "Description", "Resolved"));
	}

	public List<Incident> findAll() {
		return incidents;
	}

	public Incident save(Incident incident) {
		if (incident.getId() == null) {
			incident.setId(++incidentsCount);
		}
		incidents.add(incident);
		return incident;
	}

	public Incident deleteById(Integer id) {
		Iterator<Incident> iterator = incidents.iterator();
		while (iterator.hasNext()) {
			Incident incident = iterator.next();
			if (incident.getId() == id) {
				iterator.remove();
				return incident;
			}
		}
		return null;
	}

	public Incident update(int id, Incident incident) {

		Incident ExistingIncident = findById(id);

		if (Objects.nonNull(ExistingIncident)) {
			currentIndex = incidents.indexOf(ExistingIncident);

			ExistingIncident.setTopic(incident.getTopic());
			ExistingIncident.setDescription(incident.getDescription());
			ExistingIncident.setStatus(incident.getStatus());

			incidents.set(currentIndex, ExistingIncident);

			return ExistingIncident;
		}

		return null;
	}

	public Incident findById(Integer id) {
		for (Incident incident : incidents) {
			if (incident.getId() == id) {
				return incident;
			}
		}
		return null;
	}
}