package com.soprasteria.springboottesthibernate.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.springboottesthibernate.controller.EmployeeController;
import com.soprasteria.springboottesthibernate.entity.Employee;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeException;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeNotFoundException;
import com.soprasteria.springboottesthibernate.exceptions.EmptyEmployeeListException;
import com.soprasteria.springboottesthibernate.exceptions.NameOrSurnameEmptyException;
import com.soprasteria.springboottesthibernate.exceptions.NotValidEmployeeValuesException;
import com.soprasteria.springboottesthibernate.exceptions.Under18YearsOldException;
import com.soprasteria.springboottesthibernate.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeControllerImpl implements EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	//CREATE
	
	@PostMapping("/newEmployee")
	public ResponseEntity<Object> createNewEmployee(@RequestBody Employee e) {
		try {
			return new ResponseEntity<Object>(employeeService.saveEmp(e), HttpStatus.CREATED);
		} catch (EmployeeException e1) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//READ
	
	@GetMapping("/allEmps")
	public ResponseEntity<Object> getAllEmployees() {
		try {
			return new ResponseEntity<Object>(employeeService.getAllEmps(), HttpStatus.OK);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/employeeById/{empId}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable(value = "empId") Integer empId) {
		try {
			return new ResponseEntity<Object>(employeeService.getEmpById(empId), HttpStatus.OK);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//UPDATE
	
	@PutMapping("/updateEmployee")
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee e) {
		try {
			return new ResponseEntity<Object>(employeeService.updateEmp(e), HttpStatus.OK);
		} catch (NotValidEmployeeValuesException e1) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (EmployeeException e2) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e2.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE
	
	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "empId") Integer empId) {
		try {
			employeeService.getEmpById(empId);
			employeeService.deleteEmp(empId);
			return new ResponseEntity<Object>("Employee deleted successfully!", HttpStatus.OK);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/empsOverAge/{empAge}")
	public ResponseEntity<Object> getAllEmployeesOverAge(@PathVariable(value = "empAge") Integer empAge) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<Object>(employeeService.getEmpsOverAge(empAge), HttpStatus.OK);
		} catch(Under18YearsOldException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(EmployeeException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/empsWithNameLong/{empNameLength}")
	public ResponseEntity<Object> getAllEmployeesWithNameLongAtLeast(@PathVariable(value = "empNameLength") Integer empNameLength) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<Object>(employeeService.getEmpsWithNameLongAtLeast(empNameLength), HttpStatus.OK);
		} catch(NotValidEmployeeValuesException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(EmployeeException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
