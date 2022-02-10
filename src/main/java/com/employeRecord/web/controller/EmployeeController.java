package com.employeRecord.web.controller;


import com.employeRecord.data.dto.EmployeeDto;
import com.employeRecord.data.models.Employee;
import com.employeRecord.service.employee.EmployeeService;
import com.employeRecord.web.exceptions.EmployeeLogicException;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto){
        try {
            Employee savedEmployee = employeeService.createEmployee(employeeDto);
            return ResponseEntity.ok().body(savedEmployee);
        } catch (EmployeeLogicException | IllegalArgumentException d) {
            return ResponseEntity.badRequest().body(d.getMessage());
        }
    }
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody JsonPatch employeePatch){
        try {
            Employee updatedEmployee = employeeService.updateEmployeeRecord(id, employeePatch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        } catch (EmployeeLogicException f) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(f.getMessage());
        }
    }
}
