package cinema.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.controller.model.CinemaData;
import cinema.controller.model.CinemaData.CinemaEmployee;
import cinema.dao.CinemaDao;
import cinema.dao.EmployeeDao;
import cinema.entity.Cinema;
import cinema.entity.Employee;

@Service
public class CinemaService {
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private EmployeeDao employeeDao;

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
	employee.setEmployeeFirstName(employee.getEmployeeFirstName());
	employee.setEmployeeLastName(employee.getEmployeeLastName());
	employee.setEmployeePhone(employee.getEmployeePhone());
	employee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
}

	private Employee findOrCreateEmployee(Long cinemaId, Long employeeId) {
	if(Objects.isNull(employeeId)) {
		return new Employee();
	} 
	return findEmployeeById(cinemaId, employeeId);
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

}
