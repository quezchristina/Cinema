package cinema.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.controller.model.CinemaData;
import cinema.dao.CinemaDao;
import cinema.entity.Cinema;

@Service
public class CinemaService {
	
	@Autowired
	private CinemaDao cinemaDao;

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

}
