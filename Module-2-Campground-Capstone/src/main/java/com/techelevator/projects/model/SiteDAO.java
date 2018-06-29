package com.techelevator.projects.model;

import java.util.TreeMap;

public interface SiteDAO {

	public TreeMap<Integer, Site> getAvailableSites(Long campgroundId, String fromDate, String toDate);
	public boolean checkSiteOccupancy(Site currSite, String fromDate, String toDate);

}
