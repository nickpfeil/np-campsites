package com.techelevator.projects.jdbc;

import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public TreeMap<Integer, Park> getAllParks() {
		TreeMap<Integer, Park> parksMap = new TreeMap<Integer, Park>();
		String sqlReturnAllParks = "SELECT * " +
								  "FROM park " +
								  "Order By name;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllParks);
		int counter = 1;
		while (results.next()) {
			Park temp = mapRowToPark(results);
			parksMap.put(counter, temp);
			++counter;
		}

		return parksMap;
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park newPark = new Park();
		newPark.setParkId(results.getLong("park_id"));
		newPark.setName(results.getString("name"));
		newPark.setLocation(results.getString("location"));
		newPark.setEstablishDate(results.getDate("establish_date"));
		newPark.setArea(results.getInt("area"));
		newPark.setVisitors(results.getInt("visitors"));
		newPark.setDescription(results.getString("description"));
		return newPark;
	}
}
