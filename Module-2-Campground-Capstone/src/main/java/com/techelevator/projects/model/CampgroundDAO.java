package com.techelevator.projects.model;

import java.util.TreeMap;

public interface CampgroundDAO {
	public TreeMap<Integer, Campground> getAllCampgrounds(Park currPark);
}
