package com.employeRecord.data.repository;

import com.employeRecord.data.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
//@Sql(scripts = {"/db/insert.sql"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Adding a new employee")
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
}