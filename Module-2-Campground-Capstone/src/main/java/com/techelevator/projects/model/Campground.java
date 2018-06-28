package com.techelevator.projects.model;

import java.math.BigDecimal;
import java.util.TreeMap;

public class Campground {
	
	private long campgroundId;
	private long parkId;
	private String name;
	private String openFromMm;
	private String openToMm;
	private BigDecimal dailyFee;
	
	
	public long getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	
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
	
	public String getOpenFromMm() {
		return openFromMm;
	}
	
	public void setOpenFromMm(String openFromMm) {
		this.openFromMm = openFromMm;
	}
	
	public String getOpenToMm() {
		return openToMm;
	}
	
	public void setOpenToMm(String openToMm) {
		this.openToMm = openToMm;
	}
	
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	@Override
	public String toString() {
		return String.format("%1$-20s %2$-15s %3$-15s %4$-15s" , this.getName() , this.numberMonthToMonthName(Integer.parseInt(this.getOpenFromMm())) , 
															    this.numberMonthToMonthName(Integer.parseInt(this.getOpenToMm())), "$" + this.getDailyFee() + "0");
	}
	
	public String numberMonthToMonthName(int monthNum) {
		switch (monthNum) {
			case 1:
				return "January";
			case 2:
				return "February";
			case 3:
				return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August";
			case 9:
				return "September";
			case 10:
				return "October";
			case 11:
				return "November";
			case 12:
				return "December";
			default:
				break;
		}
		return null;
	}
}
