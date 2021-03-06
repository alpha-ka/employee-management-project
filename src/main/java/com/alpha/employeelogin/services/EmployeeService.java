package com.alpha.employeelogin.services;

import java.text.ParseException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import com.alpha.employeelogin.dto.EmployeeDTO;
import com.alpha.employeelogin.model.Employee;

public interface EmployeeService extends UserDetailsService {
		
	String save(EmployeeDTO employeeDto)  throws ParseException ;

 Employee employeePage(String email , Model model);
}
