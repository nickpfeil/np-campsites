package com.techelevator.projects.jdbc;

import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.Site;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public TreeMap<Integer, Reservation> searchAllReservations(int reservationId) {
		TreeMap<Integer, Reservation> reservationsMap = new TreeMap<Integer, Reservation>();
		String sqlReturnAllReservations = "SELECT * " +
								  		 "FROM reservation " +
								  		 "WHERE reservation_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllReservations, reservationId);
		int counter = 1;
		while (results.next()) {
			Reservation temp = mapRowToReservation(results);
			reservationsMap.put(counter, temp);
			++counter;
		}
		return reservationsMap;
	}
	
	public TreeMap<Integer, Reservation> getAllReservations() {
		TreeMap<Integer, Reservation> reservationsMap = new TreeMap<Integer, Reservation>();
		String sqlGetAllReservations = "SELECT * " +
									  "FROM reservation;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllReservations);
		int counter = 1;
		while(results.next()) {
			Reservation temp = mapRowToReservation(results);
			reservationsMap.put(counter, temp);
			++counter;
		}
		return reservationsMap;
	}
	
	public void createReservation(Site userSite, int reservationId, String reservationName, String fromDate, String toDate) {
		String sqlCreateReservation = "INSERT INTO reservation " +
									 "		(reservation_id, site_id, name, from_date, to_date, create_date) " +
									 "VALUES(" + reservationId + ", " + userSite.getSiteId() + ", " + reservationName + ", " +
									 fromDate + ", " + toDate + ", CURRENT_TIMESTAMP);";
		jdbcTemplate.update(sqlCreateReservation);
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		newReservation.setReservationId(results.getLong("reservation_id"));
		newReservation.setSiteId(results.getLong("site_id"));
		newReservation.setName(results.getString("name"));
		newReservation.setFromDate(results.getDate("from_date"));
		newReservation.setToDate(results.getDate("to_date"));
		newReservation.setCreateDate(results.getDate("create_date"));
		return newReservation;
	}
}
