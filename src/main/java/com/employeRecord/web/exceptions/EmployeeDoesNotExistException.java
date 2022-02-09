package com.employeRecord.web.exceptions;

public class EmployeeDoesNotExistException extends Throwable {
    public EmployeeDoesNotExistException(String s) {
        super(s);
    }
}
