package cinema.controller.model;

import java.util.HashSet;
import java.util.Set;

import cinema.entity.Cinema;
import cinema.entity.Customer;
import cinema.entity.Employee;
import cinema.entity.Show;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CinemaData {
	//copy entities expect 
	private Long cinemaId;
	private String cinemaName;
	private String cinemaAddress;
	private String cinemaCity;
	private String cinemaState;
	private String cinemaZip;
	private String cinemaPhone;
	
	private Set<CinemaCustomer> customers = new HashSet<>();
	private Set<CinemaEmployee> employees = new HashSet<>();
	private Set<CinemaShow> shows = new HashSet<>();
	
	public CinemaData(Cinema cinema) {
		cinemaId = cinema.getCinemaId();
		cinemaName = cinema.getCinemaName();
		cinemaAddress = cinema.getCinemaAddress();
		cinemaCity = cinema.getCinemaCity();
		cinemaState = cinema.getCinemaState();
		cinemaZip = cinema.getCinemaZip();
		cinemaPhone = cinema.getCinemaPhone();
		
		//loop for customer
		for (Customer customer : cinema.getCustomers()) {
			customers.add(new CinemaCustomer (customer));
			
		}
		
		//loop for employee
		for (Employee employee : cinema.getEmployees()) {
			employees.add(new CinemaEmployee (employee));
//			cinemaEmployees.add(new Employee (employee));
	
			//loop for show
			for (Show show: cinema.getShows()) {
				shows.add(new CinemaShow(show));
			}
		}
	}
	@Data
	@NoArgsConstructor
	public static class CinemaCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public CinemaCustomer (Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CinemaEmployee {
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		public CinemaEmployee (Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}
	//put shows
	@Data
	@NoArgsConstructor
	public static class CinemaShow {
		private Long showId;
		private String showName;
		private String showTime;
		private String showGenre;
		
		public CinemaShow (Show show) {
			showId = show.getShowId();
			showName = show.getShowName();
			showTime = show.getShowTime();
			showGenre = show.getShowGenre();
		}
	}

}
