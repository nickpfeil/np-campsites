package com.techelevator.projects.model;

import java.util.Date;
import java.util.TreeMap;

public interface ReservationDAO {
	public TreeMap<Integer, Reservation> searchAllReservations(Campground currCampground, String fromDate, String toDate);
	public void createReservation(Site userSite, String reservationName, String fromDate, String toDate);
	public TreeMap<Integer, Reservation> getAllReservations();
}
