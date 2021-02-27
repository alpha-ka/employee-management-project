package com.alpha.employeelogin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alpha.employeelogin.dto.EmployeeDTO;
import com.alpha.employeelogin.services.EmployeeService;


@Controller
@RequestMapping("/registration")
public class EmployeeRegistrationController {

	
	private EmployeeService employeeService;

	public EmployeeRegistrationController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@ModelAttribute("employee")
	
	public EmployeeDTO employeeDTO()
	{
		return new EmployeeDTO();
		
	}
	
	
	
	@GetMapping
	public String showRegistrationForm()
	{
		return "registration";
		
	}
	
	@PostMapping
	public String registrationAccount(@ModelAttribute("employee") EmployeeDTO employeeDTO)
	
	{		
		return employeeService.save(employeeDTO);
			
	}
	
 
}
