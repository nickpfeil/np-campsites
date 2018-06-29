package com.techelevator.projects.jdbc;

import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public TreeMap<Integer, Site> getAvailableSites(Long campgroundId, String fromDate, String toDate){
		TreeMap<Integer, Site> sitesMap = new TreeMap<Integer, Site>();
		String sqlReturnAllSites = "SELECT * " +
								  "FROM site " +
								  "		JOIN reservation ON reservation.site_id = site.site_id " +
								  "WHERE site.campground_id = ? AND reservation.from_date > " + fromDate + " AND reservation.to_date < "+ toDate + " " +
								  "ORDER BY site.site_number;";
		System.out.println(fromDate + " " + toDate);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllSites, campgroundId);
		while (results.next()) {
			Site temp = mapRowToSite(results);
			sitesMap.put(temp.getSiteNumber(), temp);
		}
		return sitesMap;
	}
	
	public boolean checkSiteOccupancy(Site currSite, String fromDate, String toDate) {
		int count = 0;
		String sqlReturnAllReservations = "SELECT count(*) AS count " +
										  "FROM reservation " +
										  "JOIN site ON reservation.site_id = site.site_id " +
										  "WHERE reservation.from_date > " + fromDate + " AND " + "reservation.to_date > " + toDate + " " +
										  "AND site.site_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllReservations, currSite.getSiteId());
		while (results.next()) {
			count = results.getInt("count");
		}
		return count <= currSite.getMaxOccupancy();
	}
	
	public Site mapRowToSite(SqlRowSet results) {
		Site newSite = new Site();
		newSite.setSiteId(results.getLong("site_id"));
		newSite.setCampgroundId(results.getInt("campground_id"));
		newSite.setSiteNumber(results.getInt("site_number"));
		newSite.setMaxOccupancy(results.getInt("max_occupancy"));
		newSite.setAccesible(results.getBoolean("accesible"));
		newSite.setMaxRvLength(results.getInt("max_rv_length"));
		newSite.setUtilities(results.getBoolean("utilities"));
		return newSite;
	}
}
