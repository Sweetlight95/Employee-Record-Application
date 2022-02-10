package com.employeRecord.service.employee;

import com.employeRecord.data.dto.EmployeeDto;
import com.employeRecord.data.models.Employee;
import com.employeRecord.data.repository.EmployeeRepository;
import com.employeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.employeRecord.web.exceptions.EmployeeLogicException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employee.getPhoneNumber());

        return employeeRepository.save(employee);
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
    private Employee saveOrUpdate(Employee employee) throws EmployeeLogicException {
        if (employee == null){
            throw new EmployeeLogicException("Employee cannot be null");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeRecord(Long employeeId, JsonPatch employeePatch) throws EmployeeLogicException{
        Optional<Employee> employeeQuery = employeeRepository.findById(employeeId);
        if(employeeQuery.isEmpty()){
            throw new EmployeeLogicException("Employee with ID "+ employeeId + " Does not exists");
        }
        Employee targetEmployee = employeeQuery.get();
        try {
            targetEmployee =  applyPatchToEmployee(employeePatch, targetEmployee);
            log.info("Employee after patch -> {}", targetEmployee);
                return  saveOrUpdate(targetEmployee);
        } catch (JsonPatchException | JsonProcessingException | EmployeeLogicException je) {
            throw new EmployeeLogicException("Update Failed");
        }
    }


    private Employee applyPatchToEmployee(JsonPatch employeePatch, Employee targetEmployee) throws JsonProcessingException, JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = employeePatch.apply(objectMapper.convertValue(targetEmployee, JsonNode.class));

        return objectMapper.treeToValue(patched, Employee.class);
    }
}
