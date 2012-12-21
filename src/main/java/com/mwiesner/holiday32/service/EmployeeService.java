package com.mwiesner.holiday32.service;

import java.util.List;

import com.mwiesner.holiday32.domain.Employee;

public interface EmployeeService { 

	public abstract long countAllEmployees();


	public abstract void deleteEmployee(Employee employee);


	public abstract Employee findEmployee(Long id);


	public abstract List<Employee> findAllEmployees();


	public abstract List<Employee> findEmployeeEntries(int firstResult, int maxResults);


	public abstract void saveEmployee(Employee employee);


	public abstract Employee updateEmployee(Employee employee);

}
