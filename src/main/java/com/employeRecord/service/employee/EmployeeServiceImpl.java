package com.employeRecord.service.employee;

import com.employeRecord.data.dto.EmployeeDto;
import com.employeRecord.data.models.Employee;
import com.employeRecord.data.repository.EmployeeRepository;
import com.employeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.employeRecord.web.exceptions.EmployeeLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) throws EmployeeLogicException {
        if(employeeDto ==  null) {
            throw new IllegalArgumentException("The Argument cannot be null");
        }
        Optional<Employee> query = employeeRepository.findByFirstName(employeeDto.getFirstName());
        if(query.isPresent()){
            throw new EmployeeLogicException("Employee with firstname" + employeeDto.getFirstName() + " already exists");
        }
        return null;
    }

    @Override
    public Employee findEmployeeById(Long employeeId) throws EmployeeDoesNotExistException {
        if (employeeId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<Employee> queryResult = employeeRepository.findById(employeeId);
        if (queryResult.isPresent()) {
            return queryResult.get();
        }
        throw new EmployeeDoesNotExistException("Employee with ID : " + employeeId + " : does not exists");
    }

//    @Override
//    public Employee updateEmployeeRecord(Long employeeId, JsonPatch employeePatch) {
//        return null;
//    }
}
