package com.alpha.employeelogin.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alpha.employeelogin.dao.EmployeeRepository;
import com.alpha.employeelogin.dao.OOFRepository;
import com.alpha.employeelogin.dto.EmployeeDTO;
import com.alpha.employeelogin.model.Employee;
import com.alpha.employeelogin.model.EmployeeRole;



@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	
	private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	
	
	
	@Autowired
	private OOFRepository oofRepo;

	@Autowired
	private NotificationServiceImpl notification;

	@Value("${spring.signature}")
	private String signature;
	
	@Value("${spring.companyid}")
	private String company;

	@Value("${spring.location}")
	private String location;
	
	 
	private EmployeeRepository empRepo;
	
	
	public EmployeeServiceImpl(EmployeeRepository empRepo) {
		super();
		this.empRepo = empRepo;
	}



	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		Employee employee=empRepo.findByEmail(username);
		if(employee==null)
			throw new UsernameNotFoundException("Employee not found");
		return new org.springframework.security.core.userdetails.User(employee.getEmail(),employee.getPassword(),mapRolesToAuthorities(employee.getRole()));
		
	}

	
	
	 private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < EmployeeRole > roles) {
	        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	    }


//	 
//	 @Override
//	 public Collection<? extends GrantedAuthority> getAuthorities() {
//	     Set<Role> roles = user.getRoles();
//	     List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//	      
//	     for (Role role : roles) {
//	         authorities.add(new SimpleGrantedAuthority(role.getName()));
//	     }
//	      
//	     return authorities;
//	 }
		@Override
		public String save(EmployeeDTO employeeDto) {
			
			
			Employee exitsingemployee = empRepo.findByEmail(employeeDto.getEmail());
	 	if (exitsingemployee == null) 
	 	{
			Employee employee=new Employee(
					employeeDto.getFirstname(), 
					employeeDto.getLastname(),
					employeeDto.getEmail(),
					passwordEncoder.encode( employeeDto.getPassword()),
					employeeDto.getDob(), 
					employeeDto.getDepartment(),
					employeeDto.getDesignation(),
					employeeDto.getStatus()
					,Arrays.asList(new EmployeeRole("USER")));	
		
			
			String to = employee.getEmail();
			String subject = "Registration";

			Map<String, Object> templatemodel = new HashMap<String, Object>();
			templatemodel.put("name", employee.getFirstname() + " " + employee.getLastname());
			templatemodel.put("signature", signature);
			templatemodel.put("location", location);
			
			System.out.println(employee);
			empRepo.save(employee);		 

			try {

				notification.sendRegistrationMail(to, subject, templatemodel);
				// notification.sendRegMail(emp);
			} catch (Exception e) {
				logger.info("Error sending mail..................");

			}
			return "redirect:/registration?success";
			
	 	}
	 	else
	 	{
	 		return "redirect:/registration?error";		
	 		
	 	}
		 
			
			
		}





		@Override
		public Employee employeePage(String email, Model model) {
			Employee employee = empRepo.findByEmail(email);
			
			System.out.println(employee);
			Collection<EmployeeRole> roles=employee.getRole();
			System.out.println(roles);
			
			String admin="";
			
			for (EmployeeRole role : roles)
			{
				if (role.getName().equals("ADMIN"))
				{
					admin="Yes";
				}
				else
				{
					admin="No";
				}
				
			}
			
			
			System.out.println("Admin? "+admin);
			
			
			model.addAttribute("admin",admin);
			model.addAttribute("employee", employee);
			model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
			return employee;
		}

 
}
