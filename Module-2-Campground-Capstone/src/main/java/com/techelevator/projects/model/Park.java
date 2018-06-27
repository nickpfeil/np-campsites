package com.techelevator.projects.model;

import java.util.Date;

public class Park {
	
	private long parkId;
	private String name;
	private String location;
	private Date establishDate;
	private int visitors;
	private int area;
	private String description;
	
	
	public long getParkId() {
		return parkId;
	}
	
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Date getEstablishDate() {
		return establishDate;
	}
	
	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}
	
	public int getVisitors() {
		return visitors;
	}
	
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	
	public int getArea() {
		return area;
	}
	
	public void setArea(int area) {
		this.area = area;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
