package com.techelevator.projects.model;

import java.util.TreeMap;

public interface ReservationDAO {
	public TreeMap<Integer, Reservation> searchAllReservations(int reservationId);
	public void createReservation(Site userSite, int reservationId, String reservationName, String fromDate, String toDate);
	public TreeMap<Integer, Reservation> getAllReservations();
}
