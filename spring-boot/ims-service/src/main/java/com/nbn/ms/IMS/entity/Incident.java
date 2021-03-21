package com.nbn.ms.IMS.entity;

import java.util.Date;

public class Incident {

	private Integer id;
	private Date issueDate;
	private String topic;
	private String description;
	private String status;
	
	public Incident(Integer id, Date issueDate, String topic, String description, String status) {
		super();
		this.id = id;
		this.issueDate = issueDate;
		this.topic = topic;
		this.description = description;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", issueDate=" + issueDate + ", topic=" + topic + ", description=" + description
				+ ", status=" + status + "]";
	}
	
}

