package com.soprasteria.springboottesthibernate.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprasteria.springboottesthibernate.entity.Employee;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeException;
import com.soprasteria.springboottesthibernate.exceptions.EmployeeNotFoundException;
import com.soprasteria.springboottesthibernate.exceptions.EmptyEmployeeListException;
import com.soprasteria.springboottesthibernate.exceptions.NameOrSurnameEmptyException;
import com.soprasteria.springboottesthibernate.exceptions.NegativeSalaryException;
import com.soprasteria.springboottesthibernate.exceptions.NotValidEmployeeValuesException;
import com.soprasteria.springboottesthibernate.exceptions.Under18YearsOldException;
import com.soprasteria.springboottesthibernate.repository.EmployeeRepository;
import com.soprasteria.springboottesthibernate.service.AgePredicate;
import com.soprasteria.springboottesthibernate.service.EmployeeService;
import com.soprasteria.springboottesthibernate.service.NameLengthPredicate;
import com.soprasteria.springboottesthibernate.service.SurnameComparator;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee saveEmp(Employee e) throws EmployeeException {
		if(e.getName().isBlank() || e.getSurname().isBlank()) {
			throw new NameOrSurnameEmptyException("name and surname cannot be empty!");
		} else if(e.getSalary() < 0) {
			throw new NegativeSalaryException("salary cannot be negative!");
		} else if(e.getAge() < 18) {
			throw new Under18YearsOldException("the employee cannot be under 18 years old!");
		}
		return employeeRepository.save(e);
	}
	
	public List<Employee> getAllEmps() throws EmployeeException {
		List<Employee> employees = employeeRepository.findAll();
		if(employees.size() == 0) {
			throw new EmptyEmployeeListException("there are no employees!");
		} else {
			
			/*
			 * Possiamo quindi sostituire l'implementazione di un'interfaccia 
			 * funzionale (in questo esempio, Comparator<T>):
			 * 1) con una lambda expression
			 * 2) con una classe anonima
			 */
			
			//UTILIZZO DI UNA LAMBDA EXPRESSION PER ORDINARE GLI EMPLOYEE IN BASE AL COGNOME
//			Collections.sort(employees, (Employee e1, Employee e2) -> e1.getSurname().compareToIgnoreCase(e2.getSurname()));
			
//			Collections.sort(employees, (e1, e2) -> e1.getSurname().compareToIgnoreCase(e2.getSurname()));
			
			//REFERENCE A METODO
//			Comparator<Employee> c = new Comparator<>() {
//				
//				@Override
//				public int compare(Employee o1, Employee o2) {
//					// TODO Auto-generated method stub
//					return o1.getSurname().compareToIgnoreCase(o2.getSurname());
//				}
//			};
			
			Comparator<Employee> c = new SurnameComparator();
			Collections.sort(employees, c::compare);
			
			//SE USO UN RAW TYPE
//			ArrayList l = new ArrayList();
//			String s = (String) l.get(0);
			
//			Consumer<String> sc;
			
			//REFERENCE A METODO DI ISTANZA DI UN CERTO TIPO
//			List<String> l = new ArrayList<>();
//			Collections.sort(l,String::compareToIgnoreCase);
			
//			Collections.sort(l, new Comparator<String>() {
//
//				@Override
//				public int compare(String o1, String o2) {
//					// TODO Auto-generated method stub
//					return o1.compareToIgnoreCase(o2);
//				}
//			});
			
			//QUESTO NON SI PUO' FARE
//			List<String> empSurnames = new ArrayList<String>();
//			employees.stream().forEach(e -> empSurnames.add(e.getSurname()));
//			Collections.sort(empSurnames, String::compareToIgnoreCase);
			
			//UTILIZZO DI UNA CLASSE ANONIMA PER ORDINARE GLI EMPLOYEE IN BASE AL COGNOME
//			Collections.sort(employees, new Comparator<Employee>() {
//
//				@Override
//				public int compare(Employee o1, Employee o2) {
//					// TODO Auto-generated method stub
//					return o1.getSurname().compareToIgnoreCase(o2.getSurname());
//				}
//			});
			return employees;
		}
	}
	
	public Employee getEmpById(Integer id) throws EmployeeException {
		Optional<Employee> findById = employeeRepository.findById(id);
		if(!findById.isPresent()) {
			throw new EmployeeNotFoundException("the employee with id " + id + " does not exist!");
		} else {
			return findById.get();
		}
	}
	
	public Employee updateEmp(Employee e) throws EmployeeException {
		Optional<Employee> findById = employeeRepository.findById(e.getId());
		if(!findById.isPresent()) {
			throw new EmployeeNotFoundException("the employee with id " + e.getId() + " cannot be updated because does not exist!");
		} else {
			if(e.getName().isBlank() || e.getSurname().isBlank()) {
				throw new NameOrSurnameEmptyException("name and surname cannot be empty!");
			} else if(e.getSalary() < 0) {
				throw new NegativeSalaryException("salary cannot be negative!");
			} else if(e.getAge() < 18) {
				throw new Under18YearsOldException("the employee cannot be under 18 years old!");
			}
			return employeeRepository.save(e);
		}
	}
	
	public void deleteEmp(Integer id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> getEmpsOverAge(Integer age) throws EmployeeException {
		// TODO Auto-generated method stub
		List<Employee> employees = employeeRepository.findAll();
		if(employees.size() == 0) {
			throw new EmptyEmployeeListException("there are no employees!");
		} else if(age < 18) {
			throw new Under18YearsOldException("the employee must be adult (18+)!");
		} else {
			Comparator<Employee> c = new SurnameComparator();
			Collections.sort(employees, c::compare);
			Predicate<Employee> p = new AgePredicate(age);
			List<Employee> filteredEmployees = employees.stream().filter(p::test).toList();
			if(filteredEmployees.size() == 0) {
				throw new EmptyEmployeeListException("there are no employees over the specified age!");
			}
			return filteredEmployees;
		}
	}

	@Override
	public List<Employee> getEmpsWithNameLongAtLeast(Integer length) throws EmployeeException {
		// TODO Auto-generated method stub
		List<Employee> employees = employeeRepository.findAll();
		if(employees.size() == 0) {
			throw new EmptyEmployeeListException("there are no employees!");
		} else if(length <= 0) {
			throw new NameOrSurnameEmptyException("employee's name cannot be empty!");
		} else {
			Comparator<Employee> c = new SurnameComparator();
			Collections.sort(employees, c::compare);
//			List<Employee> filteredEmployees = employees.stream().filter(e -> e.getName().length() >= length).toList();
			Predicate<Employee> p = new NameLengthPredicate(length);
			List<Employee> filteredEmployees = employees.stream().filter(p::test).toList();
			if(filteredEmployees.size() == 0) {
				throw new EmptyEmployeeListException("there are no employees with name long at least " + length + " chars!");
			}
			return filteredEmployees;
		}
	}

}
