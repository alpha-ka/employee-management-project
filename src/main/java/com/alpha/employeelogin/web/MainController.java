package com.alpha.employeelogin.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alpha.employeelogin.dto.EmployeeDTO;
import com.alpha.employeelogin.model.Employee;
import com.alpha.employeelogin.services.EmployeeService;

@Controller
public class MainController {

 
	
	private EmployeeService employeeService;

	public MainController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	
	@GetMapping("/")
	public String homepage(@AuthenticationPrincipal UserDetails userdetail,Model model)
	{
		String email=userdetail.getUsername();
	 	employeeService.employeePage(email,model);
				
		
		return "index";
	}
	
	 
	
	
}
