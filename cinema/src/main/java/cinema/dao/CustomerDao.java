package cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
