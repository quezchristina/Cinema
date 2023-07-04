package cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
