package com.soprasteria.springboottesthibernate.service;

import java.util.List;

import com.soprasteria.springboottesthibernate.entity.Employee;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeException;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeNotFoundException;
import com.soprasteria.springboottesthibernate.exceptions.EmptyEmployeeListException;
import com.soprasteria.springboottesthibernate.exceptions.NameOrSurnameEmptyException;
import com.soprasteria.springboottesthibernate.exceptions.NotValidEmployeeValuesException;
import com.soprasteria.springboottesthibernate.exceptions.Under18YearsOldException;

public interface EmployeeService {
	
	public Employee saveEmp(Employee e) throws EmployeeException;
	
	public List<Employee> getAllEmps() throws EmployeeException;
	
	public Employee getEmpById(Integer id) throws EmployeeException;
	
	public List<Employee> getEmpsOverAge(Integer age) throws EmployeeException;
	
	public List<Employee> getEmpsWithNameLongAtLeast(Integer length) throws EmployeeException;
	
	public Employee updateEmp(Employee e) throws EmployeeException;
	
	public void deleteEmp(Integer id);

}
