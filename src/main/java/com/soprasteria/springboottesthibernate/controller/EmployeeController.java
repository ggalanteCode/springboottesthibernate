package com.soprasteria.springboottesthibernate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.soprasteria.springboottesthibernate.entity.Employee;

public interface EmployeeController {
	
	public ResponseEntity<Object> createNewEmployee(@RequestBody Employee e);
	
	public ResponseEntity<Object> getAllEmployees();
	
	public ResponseEntity<Object> getAllEmployeesOverAge(@PathVariable(value = "empAge") Integer empAge);
	
	public ResponseEntity<Object> getAllEmployeesWithNameLongAtLeast(@PathVariable(value = "empNameLength") Integer empNameLength);
	
	public ResponseEntity<Object> getEmployeeById(@PathVariable(value = "empId") Integer empId);
	
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee e);
	
	public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "empId") Integer empId);

}
