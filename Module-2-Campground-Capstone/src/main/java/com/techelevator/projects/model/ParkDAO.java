package com.techelevator.projects.model;

import java.util.TreeMap;

public interface ParkDAO {

	public TreeMap<Integer, Park> getAllParks();
	
	public void viewAllCampgrounds(Park currPark);
	
}
