package com.techelevator.projects.model;

import java.util.TreeMap;

public interface SiteDAO {

	TreeMap<Integer, Site> getAvailableSites(Long campgroundId, String fromDate, String toDate);

}
