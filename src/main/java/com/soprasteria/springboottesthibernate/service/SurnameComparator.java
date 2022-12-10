package com.soprasteria.springboottesthibernate.service;

import java.util.Comparator;

import com.soprasteria.springboottesthibernate.entity.Employee;

public class SurnameComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		// TODO Auto-generated method stub
		return o1.getSurname().compareToIgnoreCase(o2.getSurname());
	}

}
