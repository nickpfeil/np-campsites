package com.techelevator.projects.jdbc;

import java.util.Date;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.Site;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public TreeMap<Integer, Reservation> searchAllReservations(Campground currCampground, String fromDate, String toDate) {
		TreeMap<Integer, Reservation> reservationsMap = new TreeMap<Integer, Reservation>();
		String sqlReturnAllReservations = "SELECT * " +
								  "FROM site " +
								  "JOIN campground ON site.campground_id = campground.campground_id " +
								  "WHERE campground.campground_id = ? AND site.site_id NOT IN(SELECT site.site_id " +
								  "FROM site JOIN reservation ON site.site_id = reservation.site_id " + 
								  "WHERE " + fromDate + " >= reservation.from_date " +
								  "AND " + toDate + " <= reservation.to_date) LIMIT 5;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllReservations, currCampground.getCampgroundId());
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
	
	public void createReservation(Site userSite, String reservationName, String fromDate, String toDate) {
		String sqlCreateReservation = "INSERT INTO reservation " +
									 "		(site_id, name, from_date, to_date, create_date) " +
									 "VALUES(" + userSite.getSiteId() + ", " + reservationName + ", " +
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
