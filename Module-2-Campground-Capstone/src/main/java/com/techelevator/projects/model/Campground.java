package com.techelevator.projects.model;

import java.math.BigDecimal;

public class Campground {
	
	private long campgroundId;
	private long parkId;
	private String name;
	private int openFromMm;
	private int openToMm;
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
	
	public int getOpenFromMm() {
		return openFromMm;
	}
	
	public void setOpenFromMm(int i) {
		this.openFromMm = i;
	}
	
	public int getOpenToMm() {
		return openToMm;
	}
	
	public void setOpenToMm(int openToMm) {
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
		return String.format("%1$-20s %2$-15s %3$-15s %4$-15s" , this.getName() , this.numberMonthToMonthName(this.getOpenFromMm()) , 
															    this.numberMonthToMonthName(this.getOpenToMm()), "$" + this.getDailyFee() + "0");
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
