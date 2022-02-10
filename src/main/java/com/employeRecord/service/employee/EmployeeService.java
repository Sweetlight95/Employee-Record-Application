package com.employeRecord.service.employee;

import com.employeRecord.data.dto.EmployeeDto;
import com.employeRecord.data.models.Employee;
import com.employeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.employeRecord.web.exceptions.EmployeeLogicException;
import com.github.fge.jsonpatch.JsonPatch;


import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee createEmployee(EmployeeDto employeeDto) throws EmployeeLogicException;

    Employee findEmployeeById(Long employeeId) throws EmployeeDoesNotExistException;

    Employee updateEmployeeRecord(Long employeeId, JsonPatch employeePatch) throws EmployeeLogicException;
}
