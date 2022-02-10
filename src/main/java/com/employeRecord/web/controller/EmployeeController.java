package com.employeRecord.web.controller;


import com.employeRecord.data.models.Employee;
import com.employeRecord.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<?> findAllEmployee(){
        List<Employee> employeeList = employeeService.getAllEmployee();
        return ResponseEntity.ok().body(employeeList);

    }
}
