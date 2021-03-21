package com.nbn.ms.AMS.model;

import java.io.Serializable;

public class AMSResponseDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer incidentId;
	private String status;
	
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "AMSResponseDto [incidentId=" + incidentId + ", status=" + status + "]";
	}

	
}
