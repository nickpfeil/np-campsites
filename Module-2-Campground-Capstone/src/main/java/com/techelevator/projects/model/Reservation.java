package com.techelevator.projects.model;

import java.util.Date;

public class Reservation {
	
	private long reservationId;
	private long siteId;
	private String name;
	private Date fromDate;
	private Date toDate;
	private Date createDate;
	
	
	public long getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	
	public long getSiteId() {
		return siteId;
	}
	
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
	public String toString() {
		return String.format("%1$-20s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s" , this.getReservationId(), this.getSiteId(), this.getName(), this.getFromDate()
																			  , this.getToDate(), this.getCreateDate());
	}
}
