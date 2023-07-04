package cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cinema.controller.model.CinemaData;
import cinema.controller.model.CinemaData.CinemaEmployee;
import cinema.service.CinemaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cinema")
@Slf4j
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;

	@PostMapping("/cinema")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CinemaData insertCinema(@RequestBody CinemaData cinemaData) {
		log.info("Creating cinema {}", cinemaData);
		return cinemaService.saveCinema(cinemaData);
	}

	@PutMapping("/cinema/{cinemaId}")
	public CinemaData updateCinema(@PathVariable Long cinemaId, @RequestBody CinemaData cinemaData) {
		cinemaData.setCinemaId(cinemaId);
		log.info("Update cinema {} for ID=" + cinemaData);
		return cinemaService.saveCinema(cinemaData);
	}

	@PostMapping("{cinemaId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CinemaEmployee insertEmployee(@PathVariable Long cinemaId, @RequestBody CinemaEmployee cinemaEmployee) {
		log.info("Adding cinema employee {} with ID=", cinemaEmployee, cinemaId);
		return cinemaService.saveEmployee(cinemaId, cinemaEmployee);

	}

	@PutMapping("/cinema/{employeeId}")
	public CinemaEmployee updateEmployee(@PathVariable Long employeeId, @RequestBody CinemaEmployee cinemaEmployee) {
		cinemaEmployee.setEmployeeId(employeeId);
		log.info("Update employee {} for ID=", cinemaEmployee);
		return cinemaService.saveEmployee(employeeId,cinemaEmployee);
	}

	@GetMapping("/cinema")
	public List<CinemaData> retrieveAllCinemas() {
		log.info("Retrieve all cinemas.");
		return cinemaService.retrieveAllCinemas();
	}

	@GetMapping("/cinema/{cinemaId}")
	public CinemaData retrieveCinemaById(@PathVariable Long cinemaId) {
		log.info("Retrieving cinema with ID={}", cinemaId);
		return cinemaService.retrieveCinemaById(cinemaId);
	}

}
