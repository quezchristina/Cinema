package cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.entity.Cinema;

public interface CinemaDao extends JpaRepository<Cinema, Long> {

}
