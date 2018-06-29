package com.techelevator.projects.model;

import java.math.BigDecimal;

public class Site {
	
	private long siteId;
	private long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accesible;
	private int maxRvLength;
	private boolean utilities;
	
	
	public long getSiteId() {
		return siteId;
	}
	
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public long getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public int getSiteNumber() {
		return siteNumber;
	}
	
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	
	public boolean isAccesible() {
		return accesible;
	}
	
	public void setAccesible(boolean accesible) {
		this.accesible = accesible;
	}
	
	public int getMaxRvLength() {
		return maxRvLength;
	}
	
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	
	public boolean isUtilities() {
		return utilities;
	}
	
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	public String toString(BigDecimal cost) {
		return String.format("%1$-20s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s" , this.getSiteNumber(), this.getMaxOccupancy(), this.isAccesible()
																			  , this.getMaxRvLength(), this.isUtilities(), "$" + cost);
	}
}
