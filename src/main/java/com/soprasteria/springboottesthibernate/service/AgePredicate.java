package com.soprasteria.springboottesthibernate.service;

import java.util.function.Predicate;

import com.soprasteria.springboottesthibernate.entity.Employee;

public class AgePredicate implements Predicate<Employee> {
	
	private int age;

	public AgePredicate(int age) {
		super();
		this.age = age;
	}

	@Override
	public boolean test(Employee t) {
		// TODO Auto-generated method stub
		return t.getAge() >= age;
	}

}
