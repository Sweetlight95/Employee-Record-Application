package com.employeRecord.data.repository;

import com.employeRecord.data.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstName(String name);
    Optional<Employee> findByLastName(String name);
}
