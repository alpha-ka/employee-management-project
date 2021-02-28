package com.alpha.employeelogin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alpha.employeelogin.dao.EmployeeRepository;
import com.alpha.employeelogin.dao.OOFRepository;
import com.alpha.employeelogin.model.Employee;


@SpringBootApplication
public class EmployeeLoginApplication implements CommandLineRunner,WebMvcConfigurer
{

	public static void main(String[] args) {
		SpringApplication.run(EmployeeLoginApplication.class, args);	
		
		
		
	}
 
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		registry
//			.addResourceHandler("/URLResource/**")
//			.addResourceLocations("/resources/");
//		
//	}

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private OOFRepository oofRepo;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		String str="06/16/1994";
//		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//	 	Date date = format.parse(str);
//		Employee emp=new Employee("Alpha","K A", "alphaka94@gmail.com", "12345678", date, "Developer", "SoftwareEngineer");
//		
//		empRepo.save(emp);
		
		
		
	}
	
	
 
	
	

}
