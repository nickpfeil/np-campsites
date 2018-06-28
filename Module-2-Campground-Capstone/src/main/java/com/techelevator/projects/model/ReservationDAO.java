package com.techelevator.projects.model;

import java.util.TreeMap;

public interface ReservationDAO {
	public TreeMap<Integer, Reservation> getAvailableReservations(Campground currCampground);
}
