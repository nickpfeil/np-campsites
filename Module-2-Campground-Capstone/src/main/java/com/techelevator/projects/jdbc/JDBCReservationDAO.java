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

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public TreeMap<Integer, Reservation> getAvailableReservations(Campground currCampground, String fromDate, String toDate) {
		TreeMap<Integer, Reservation> reservationsMap = new TreeMap<Integer, Reservation>();
		String sqlReturnAllReservations = "SELECT * " +
								  "FROM reservation " +
								  "JOIN site ON reservation.site_id = site.site_id " +
								  "JOIN campground ON site.campground_id = campground.campground_id " +
								  "WHERE campground.campground_id = ? AND reservation.from_date > ? AND reservation.to_date < ? " +
								  "ORDER BY reservation.from_date;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllReservations, currCampground.getCampgroundId(), fromDate, toDate);
		int counter = 1;
		while (results.next()) {
			Reservation temp = mapRowToReservation(results);
			reservationsMap.put(counter, temp);
			++counter;
		}
		return reservationsMap;
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		newReservation.setReservationId(results.getLong("reservation_id"));
		newReservation.setSiteId(results.getLong("site_id"));
		newReservation.setName(results.getNString("name"));
		newReservation.setFromDate(results.getDate("from_date"));
		newReservation.setToDate(results.getDate("to_date"));
		newReservation.setCreateDate(results.getDate("create_date"));
		return newReservation;
	}
}
