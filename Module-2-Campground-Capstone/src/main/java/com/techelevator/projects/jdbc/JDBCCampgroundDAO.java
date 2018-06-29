package com.techelevator.projects.jdbc;

import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;
import com.techelevator.projects.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public TreeMap<Integer, Campground> getAllCampgrounds(Park currPark) {
		TreeMap<Integer, Campground> campgroundsMap = new TreeMap<Integer, Campground>();
		String sqlReturnAllCampgrounds = "SELECT * " +
								  "FROM campground " +
								  "JOIN park ON campground.park_id = park.park_id " +
								  "WHERE park.park_id = ? " +
								  "ORDER BY campground.name;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllCampgrounds, currPark.getParkId());
		int counter = 1;
		while (results.next()) {
			Campground temp = mapRowToCampground(results);
			campgroundsMap.put(counter, temp);
			++counter;
		}
		return campgroundsMap;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground newCampground = new Campground();
		newCampground.setCampgroundId(results.getLong("campground_id"));
		newCampground.setParkId(results.getLong("park_id"));
		newCampground.setName(results.getString("name"));
		newCampground.setOpenFromMm(results.getInt("open_from_mm"));
		newCampground.setOpenToMm(results.getInt("open_to_mm"));
		newCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return newCampground;
	}
}
