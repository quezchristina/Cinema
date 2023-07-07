package cinema.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.controller.model.CinemaData;
import cinema.controller.model.CinemaData.CinemaCustomer;
import cinema.controller.model.CinemaData.CinemaEmployee;
import cinema.dao.CinemaDao;
import cinema.dao.CustomerDao;
import cinema.dao.EmployeeDao;
import cinema.entity.Cinema;
import cinema.entity.Customer;
import cinema.entity.Employee;

@Service
public class CinemaService {
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public CinemaData saveCinema(CinemaData cinemaData) {
		Long cinemaId = cinemaData.getCinemaId();
		Cinema cinema = findOrCreateCinema(cinemaData.getCinemaId());
		if(cinemaId == null) {
			cinema = findOrCreateCinema(cinemaId);
		} else {
			cinema = findCinemaById(cinemaId);
			if (cinema == null) {
				throw new NoSuchElementException("Cinema is not found with ID" + cinemaId);
			}
		}
		copyCinemaFields(cinema, cinemaData);
		Cinema savedCinema = cinemaDao.save(cinema);
		return new CinemaData(savedCinema);
	}
	
	private void copyCinemaFields(Cinema cinema, CinemaData cinemaData) {
		cinema.setCinemaId(cinemaData.getCinemaId());
		cinema.setCinemaName(cinemaData.getCinemaName());
		cinema.setCinemaAddress(cinemaData.getCinemaAddress());
		cinema.setCinemaCity(cinemaData.getCinemaCity());
		cinema.setCinemaState(cinemaData.getCinemaState());
		cinema.setCinemaZip(cinemaData.getCinemaZip());
		cinema.setCinemaPhone(cinemaData.getCinemaPhone());
	}

	private Cinema findOrCreateCinema(Long cinemaId) {
		Cinema cinema;
		if(Objects.isNull(cinemaId)) {
			cinema = new Cinema();
			
			
		} else {
			cinema = findCinemaById(cinemaId);
		}
		return cinema;
		
	}
	
	@Transactional(readOnly = true)
	private Cinema findCinemaById(Long cinemaId) {
		return cinemaDao.findById(cinemaId).orElseThrow(()-> 
			new NoSuchElementException("Cinema with ID =" + cinemaId + " was not found"));
		
	}
	
	@Transactional(readOnly = true)
	public List<CinemaData> retrieveAllCinemas() {
		//@formatter:off
		return cinemaDao.findAll()
				.stream()
				.map(CinemaData::new)
				.toList();
		//@formatter:on
		
	}
	@Transactional(readOnly = true)
	public CinemaData retrieveCinemaById(Long cinemaId) {
		Cinema cinema = findCinemaById(cinemaId);
		return new CinemaData(cinema);
	}
	
	public void deleteCinemaById(Long cinemaId) {
		Cinema cinema = findCinemaById(cinemaId);
		cinemaDao.delete(cinema);
	}
	
//Employee
	@Transactional(readOnly = false)
	public CinemaEmployee saveEmployee(Long cinemaId, CinemaEmployee cinemaEmployee) {
		Cinema cinema = findCinemaById(cinemaId);
		Long employeeId = cinemaEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(cinemaId, employeeId);
		
		copyEmployeeFields(employee, cinemaEmployee);
		
		employee.setCinema(cinema);
		cinema.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new CinemaEmployee(dbEmployee);
	}

	private void copyEmployeeFields(Employee employee, CinemaEmployee cinemaEmployee) {
	employee.setEmployeeFirstName(cinemaEmployee.getEmployeeFirstName());
	employee.setEmployeeLastName(cinemaEmployee.getEmployeeLastName());
	employee.setEmployeeId(cinemaEmployee.getEmployeeId());
	employee.setEmployeeJobTitle(cinemaEmployee.getEmployeeJobTitle());
	employee.setEmployeePhone(cinemaEmployee.getEmployeePhone());
	
}

	@Transactional(readOnly = true)
	private Employee findEmployeeById(Long cinemaId, Long employeeId) {
	Employee employee = employeeDao.findById(employeeId).orElseThrow(()->
		new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
	
	if(employee.getCinema().getCinemaId() != cinemaId) {
		throw new IllegalArgumentException
			("Employee with ID=" + employeeId + " is not an employee at this cinema with ID= " + cinemaId);
	}
	return employee;
}

	private Employee findOrCreateEmployee(Long cinemaId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		} 
		return findEmployeeById(cinemaId, employeeId);
	}
	
//CUSTOMER
	@Transactional(readOnly = false)
	public CinemaCustomer saveCustomer(Long cinemaId, CinemaCustomer cinemaCustomer) {
		Cinema cinema = findCinemaById(cinemaId);
		Long customerId = cinemaCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(cinemaId, customerId);
		
		copyCustomerFields(customer, cinemaCustomer);
		
		customer.getCinema().add(cinema);
		cinema.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new CinemaCustomer(dbCustomer);

	}

	private void copyCustomerFields(Customer customer, CinemaCustomer cinemaCustomer) {
		customer.setCustomerId(cinemaCustomer.getCustomerId());
		customer.setCustomerFirstName(cinemaCustomer.getCustomerFirstName());
		customer.setCustomerLastName(cinemaCustomer.getCustomerLastName());
		customer.setCustomerEmail(cinemaCustomer.getCustomerEmail());
		
	}

	private Customer findOrCreateCustomer(Long cinemaId, Long customerId) {
		if(Objects.isNull(customerId)) {
			return new Customer();
		}
		return findCustomerById(cinemaId, customerId);
	}

	private Customer findCustomerById(Long cinemaId, Long customerId) {
		Customer customer = customerDao.findById(customerId).orElseThrow(()-> 
			new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
		
		boolean found = false;
		
		for(Cinema cinema : customer.getCinema()) {
			if(cinema.getCinemaId() == cinemaId) {
				found = true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("Cusotmer with ID =" + customerId +
					" is not a customer at this cinema with ID=" + cinemaId);
		}
		return customer;
	}
	

}
