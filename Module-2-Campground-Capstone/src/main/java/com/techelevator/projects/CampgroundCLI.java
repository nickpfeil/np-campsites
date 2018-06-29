package com.techelevator.projects;

import java.util.TreeMap;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.jdbc.*;

import com.techelevator.projects.model.*;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {
	
	private TreeMap<Integer, Park> parksMap = new TreeMap<Integer, Park>();
	private TreeMap<Integer, Campground> campgroundsMap = new TreeMap<Integer, Campground>();
	private TreeMap<Integer, Reservation> availableReservationsMap = new TreeMap<Integer, Reservation>();
	private TreeMap<Integer, Reservation> reservationsMap = new TreeMap<Integer, Reservation>();
	private TreeMap<Integer, Site> sitesMap = new TreeMap<Integer, Site>();
	private int lastReservationId;

	Scanner userInput = new Scanner(System.in);
	
	private final String CAMPGROUND_MENU_OPTION_VIEW = "View Campgrounds";
	private final String CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION = "Search for Reservation";
	private final String CAMPGROUND_OPTION_RETURN_TO_MAIN = "Return to Parks menu";
	private final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_OPTION_VIEW, 
																		CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION, 															
																		CAMPGROUND_OPTION_RETURN_TO_MAIN};
	
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
		handleParks();
	}
	
	private void campgroundsMenu(Park currPark) {
		campgroundsMap = campgroundDAO.getAllCampgrounds(currPark);
		campgroundDAO.getAllCampgrounds(currPark);
		String campgroundHeading = String.format("%1$-23s %2$-15s %3$-15s %4$-15s", "   Name", "Open", "Close", "Daily Fee");
		
		while(true) {
			System.out.println();
			System.out.println("*** Park Campgrounds ***\n");
			System.out.println(currPark.getName() + " National Park Campgrounds");
			System.out.println("--------------------------------------------\n");
			if(campgroundsMap.size() > 0) {
				System.out.println(campgroundHeading);
			}
			for(int i = 1; i <= campgroundsMap.size(); ++i) {
				System.out.print("#" + i + " ");
				System.out.println(campgroundsMap.get(i).toString());
			}
			
			System.out.println("\n1) Search for Available Reservation");
			System.out.println("2) Return to Previous Screen");
			System.out.print("\nSelect a Command: ");
			String userChoice = userInput.next();

			if (userChoice.equals("2")) {
				return;
			} else  if (userChoice.equals("1")) {
				searchAvailableReservations();
			} else {
				System.out.println("Not Valid Input\n");
			}
		}
	}
	
	private void searchAvailableReservations() {
		String campgroundHeading = String.format("%1$-23s %2$-15s %3$-15s %4$-15s", "   Name", "Open", "Close", "Daily Fee");
		while(true) {
			System.out.println("\n*** Search for Campground Reservation ***\n");
			if(campgroundsMap.size() > 0) {
				System.out.println(campgroundHeading);
			}
			for(int i = 1; i <= campgroundsMap.size(); ++i) {
				System.out.print("#" + i + " ");
				System.out.println(campgroundsMap.get(i).toString());
			}
			System.out.print("\nWhich campground (enter 0 to cancel)? >> ");
			String userChoice = userInput.next();
			if (userChoice.equals("0")) {
				return;
			} else  if (Integer.parseInt(userChoice) > campgroundsMap.size()) {
				System.out.println("Not Valid Input\n");
			} else {
				handleReservation(userChoice);
			}
		}
	}
	
	private void handleReservation(String userChoice) {
		String reservationHeading = String.format("%1$-23s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");

		System.out.print("What is the arrival date (YYYY-MM-DD)? >> ");
		String fromDateS = userInput.next();
		int arrivalMonth = Integer.parseInt(fromDateS.substring(5, 7));
		
		LocalDate localArrivalDate = LocalDate.parse(fromDateS);
		System.out.print("What is the departure date (YYYY-MM-DD)? >> ");
		String toDateS = userInput.next();
		int departureMonth = Integer.parseInt(toDateS.substring(5, 7));
		
		LocalDate localDepartureDate = LocalDate.parse(toDateS);
		toDateS = "'" + toDateS + "'";
		fromDateS = "'" + fromDateS + "'";
		
		long daysBetween = ChronoUnit.DAYS.between(localArrivalDate, localDepartureDate);
		int choice = Integer.parseInt(userChoice);
		BigDecimal totalCost = new BigDecimal(daysBetween).multiply(campgroundsMap.get(choice).getDailyFee());

		if (campgroundsMap.get(choice).getOpenFromMm() < arrivalMonth
				&& campgroundsMap.get(choice).getOpenFromMm() < departureMonth
				&& campgroundsMap.get(choice).getOpenToMm() > arrivalMonth
				&& campgroundsMap.get(choice).getOpenToMm() > departureMonth) {
			sitesMap = siteDAO.getAvailableSites(campgroundsMap.get(Integer.parseInt(userChoice)).getCampgroundId(),
					fromDateS, toDateS);
			if (sitesMap.size() > 0) {
				System.out.println("\nResults Matching Your Search Criteria:");
				System.out.println(reservationHeading);

				for (int siteNum : sitesMap.keySet()) {
					System.out.println(sitesMap.get(siteNum).toString(totalCost));
				}

				System.out.print("Which site should be reserved (enter 0 to cancel)? >> ");
				String siteToReserve = userInput.next();
				System.out.print("What name should the reservation be made under? >> ");
				String reservationName = "'" + userInput.next() + "'";
				reservationDAO.createReservation(sitesMap.get(Integer.parseInt(siteToReserve)), lastReservationId + 1,
						reservationName, fromDateS, toDateS);
				populateReservationsMap();
				lastReservationId = reservationsMap.lastKey();
				System.out
						.println("\nReservation for " + reservationName + " with confirmation id " + lastReservationId);
				return;
			} else {
				System.out.println("You Franked it up!\n");
				return;
			}
		}
	}
	
	private void handleParks() {
		parksMap = parkDAO.getAllParks();
		populateReservationsMap();
		
		while (true) {
			System.out.println("\nPlease select a Park");
			
			for (int i = 1; i <= parksMap.size(); ++i) {
				System.out.println(i + ") " + parksMap.get(i).getName());
			}
			System.out.println("Q); Quit");
			
			System.out.print("Enter number or (Q)uit: ");
			String userChoice = userInput.next();

			if (userChoice.toUpperCase().equals("Q")) {
				userInput.close();
				System.exit(0);
			} else  if (parksMap.containsKey(Integer.parseInt(userChoice))) {
				parkMenu(parksMap.get(Integer.parseInt(userChoice)));
			} else {
				System.out.println("Not Valid Input\n");
			}

		}
	}
	
	private void populateReservationsMap() {
		reservationsMap = reservationDAO.getAllReservations();
		lastReservationId = reservationsMap.lastKey();
	}
	
	private void displayApplicationBanner() {
		System.out.println("words");
	}
	
	private void reservationMenu() {
		while (true) {
			System.out.println("\n*** Search Reservations ***");
			System.out.print("Please enter reservation id or (Q) to quit: ");
			String userId = userInput.next();
			int reservationId = Integer.parseInt(userId);
			if (userId.toUpperCase().equals("Q")) {
				return;
			} else if (reservationsMap.containsKey(reservationId)) {
				TreeMap<Integer, Reservation> userReservations = reservationDAO.searchAllReservations(reservationId);

				for (int siteId : userReservations.keySet()) {
					String reservationHeading = String.format("%1$-20s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s",
							"Reservation ID", "Site ID", "Name", "From Date", "To Date", "Date Created");
					System.out.println(reservationHeading);
					System.out.println(userReservations.get(siteId).toString());
				}
			} else {
				System.out.println("Not valid reservation code");
			}
		}
	}
//	private void printHeading(String headingText) {
//		System.out.println("\n"+headingText);
//		for(int i = 0; i < headingText.length(); i++) {
//			System.out.print("-");
//		}
//		System.out.println();
//	}
	
	private void parkMenu(Park userChoice) {
		Menu menu = new Menu(System.in, System.out);

		while (true) { 
			System.out.println();
			System.out.println("*** Park Information Screen ***\n");
			System.out.println(userChoice.getName() + " National Park");
			System.out.println("-------------------------------");
			System.out.println("Location:         " + userChoice.getLocation());
			System.out.println("Established:      " + userChoice.getEstablishDate().toString());
			System.out.println("Area:             " + userChoice.getArea());
			System.out.println("Annual Visitors:  " + userChoice.getVisitors());
			System.out.println();
			System.out.println(userChoice.getDescription());
			
			String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS); // with [] of options

			if (choice.equals(CAMPGROUND_MENU_OPTION_VIEW)) {
				campgroundsMenu(userChoice);
			} else if (choice.equals(CAMPGROUND_MENU_OPTION_SEARCH_RESERVATION)) {
				reservationMenu();
			} else if (choice.equals(CAMPGROUND_OPTION_RETURN_TO_MAIN)) {
				//return to previous menu
				return;
			}
		}
	}
}
