package com.employeRecord.data.repository;

import com.employeRecord.data.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = "/db/insert.sql")
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Add a new employee")
    void createEmployeeTest(){
        Employee employee = new Employee();
        employee.setEmail("shile@gmail.com");
        employee.setFirstName("Seeder");
        employee.setLastName("Shile");
        employee.setPhoneNumber("08062826750");
        assertThat(employee.getId()).isNull();
        employeeRepository.save(employee);
        log.info("Employee Saved :: {}", employee);
        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo("Seeder");
        assertThat(employee.getLastName()).isEqualTo("Shile");
        assertThat(employee.getDateCreated()).isNotNull();

    }
    @Test
    @DisplayName("Get all employee record")
    void getAllEmployeeTest(){
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(4);
    }
    @Test
    @DisplayName("Get a single employee by Firstname")
    void getASingleEmployeeByFirstnameTest(){

        Employee employee = employeeRepository.findByFirstName("Omo").orElse(null);
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(20);
        assertThat(employee.getLastName()).isEqualTo("Oby");
        assertThat(employee.getPhoneNumber()).isEqualTo("07022340");
        log.info("Employee retrieved :: {}", employee);
    }
    @Test
    @DisplayName("Update an employee record")
    void updateEmployeeRecordTest(){
        Employee savedEmployee = employeeRepository.findByLastName("Kunle").orElse(null);
        assertThat(savedEmployee).isNotNull();
        savedEmployee.setFirstName("Oluwatimi");
        assertThat(savedEmployee.getLastName()).isEqualTo("Kunle");
        assertThat(savedEmployee.getEmail()).isEqualTo("TimKunle@gmail.com");
        savedEmployee.setPhoneNumber("08166771857");
        employeeRepository.save(savedEmployee);
        assertThat(savedEmployee.getPhoneNumber()).isEqualTo("08166771857");
        assertThat(savedEmployee.getFirstName()).isEqualTo("Oluwatimi");
    }

    @Test
    @DisplayName("Delete an employee records")
    void deleteEmployeeRecordTest(){
          Employee employee = new Employee();
          employee.setLastName("Kim");
          employee.setFirstName("Kimoyo");
          employee.setEmail("kim@gmail.com");
          employeeRepository.save(employee);
          log.info("{}", employeeRepository.findAll());

          employeeRepository.deleteById(20L);
          Employee retrievedEmployee = employeeRepository.findById(20L).orElse(null);
          assertThat(retrievedEmployee).isNull();

          assertThat(employee.getLastName()).isEqualTo("Kim");
          assertThat(employee.getFirstName()).isEqualTo("Kimoyo");

          employeeRepository.delete(employee);

          assertThat(employeeRepository.findAll().size()).isEqualTo(3);
    }
}