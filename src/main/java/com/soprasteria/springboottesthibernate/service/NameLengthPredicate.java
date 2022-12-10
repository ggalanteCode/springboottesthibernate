package com.soprasteria.springboottesthibernate.service;

import java.util.function.Predicate;

import com.soprasteria.springboottesthibernate.entity.Employee;

public class NameLengthPredicate implements Predicate<Employee> {
	
	private int length;
	
	public NameLengthPredicate(int length) {
		super();
		this.length = length;
	}

	@Override
	public boolean test(Employee t) {
		// TODO Auto-generated method stub
		if(t.getName().length() >= length) {
			return true;
		}
		return false;
	}

}
