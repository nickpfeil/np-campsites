package com.techelevator.projects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.PrivilegedActionException;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.jdbc.JDBCCampgroundDAO;
import com.techelevator.projects.jdbc.JDBCParkDAO;
import com.techelevator.projects.jdbc.JDBCReservationDAO;
import com.techelevator.projects.jdbc.JDBCSiteDAO;
import com.techelevator.projects.model.CampgroundDAO;
import com.techelevator.projects.model.ParkDAO;
import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.SiteDAO;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {
	
	private static final String PARKS_MENU_OPTION_ACADIA = "Acadia";
	private static final String PARKS_MENU_OPTION_ARCHES = "Arches";
	private static final String PARKS_MENU_OPTION_CNVP = "Cuyahoga National Valley Park";
	private static final String PARKS_MENU_OPTION_QUIT = "Quit";
	private static final String[] PARKS_MENU_OPTIONS = new String[] { PARKS_MENU_OPTION_ACADIA, 
																	PARKS_MENU_OPTION_ARCHES, 
																	PARKS_MENU_OPTION_CNVP, 
																	PARKS_MENU_OPTION_QUIT};
	
	private static final String VIEW_CAMPGROUNDS_MENU_OPTION_AVAILABLE_RESERVATIONS = "Search for Available Reservation";
	private static final String VIEW_CAMPGROUNDS_OPTION_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] VIEW_CAMPGROUNDS_MENU_OPTIONS = new String[] { VIEW_CAMPGROUNDS_MENU_OPTION_AVAILABLE_RESERVATIONS, 
																			   VIEW_CAMPGROUNDS_OPTION_RETURN_TO_PREVIOUS};
	
	private Menu menu;
	private CampgroundDAO campgroundDAO;
	private ParkDAO parkDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
	}
	
	public void run() {
		displayApplicationBanner();	
		while(true) {
			printHeading("Parks Menu");
			String choice = (String)menu.getChoiceFromOptions(PARKS_MENU_OPTIONS);
			if(choice.equals(PARKS_MENU_OPTION_ACADIA)) {
				handleAcadia();
			} else if(choice.equals(PARKS_MENU_OPTION_ARCHES)) {
				handleArches();
			} else if(choice.equals(PARKS_MENU_OPTION_CNVP)) {
				handleCNVP();
			} else if(choice.equals(PARKS_MENU_OPTION_QUIT)) {
				System.exit(0);
			}
		}
	}
	
	private void campgroundMenu() {
		final String CAMPGROUND_MENU_OPTION_VIEW = "View Campgrounds";
		final String CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION = "Search for Reservation";
		final String CAMPGROUND_OPTION_RETURN_TO_MAIN = "Return to Parks menu";
		final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_OPTION_VIEW, 
																			CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION, 															
																			CAMPGROUND_OPTION_RETURN_TO_MAIN};
		Menu menu = new Menu(System.in, System.out);

		while (true) { // invoke getChoiceFromOptiins method with
			String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS); // with [] of options

			if (choice.equals(CAMPGROUND_MENU_OPTION_VIEW)) {
				// view campground info
			} else if (choice.equals(CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION)) {
				// search reservations
			} else if (choice.equals(CAMPGROUND_OPTION_RETURN_TO_MAIN)) {
				//return to previous menu
				return;
			}
		}
	}
	
	private void handleAcadia() {
//		Will be passing in an "Acadia" ParkObject to following method
		campgroundMenu();
	}
	
	private void handleArches() {
//		Will be passing in an "Arches" ParkObject to following method
		campgroundMenu();
	}
	
	private void handleCNVP() {
//		Will be passing in an "CNVP" ParkObject to following method
		campgroundMenu();
	}
	
	private void displayApplicationBanner() {
		System.out.println("words");
	}
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
}
