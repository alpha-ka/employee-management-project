package com.alpha.employeelogin.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alpha.employeelogin.dao.EmployeeRepository;
import com.alpha.employeelogin.dao.OOFRepository;
import com.alpha.employeelogin.exceptions.ResourceNotFoundException;
import com.alpha.employeelogin.model.Employee;
import com.alpha.employeelogin.model.EmployeeRole;
import com.alpha.employeelogin.model.OutofOffice;

import com.alpha.employeelogin.services.NotificationServiceImpl;

@Controller
public class EmpController {

//	@Value("${server.port}")
//	private String port;
//	String url = "http://localhost:"+port;
	String baseUrl;
	
	private Logger logger = LoggerFactory.getLogger(EmpController.class);
	@Autowired
	private EmployeeRepository empRepo;
	
 

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

	List<String> designationlist = Arrays.asList("", "Junior Engineer", "Software Engineer", "QA Engineer",
			"JAVA Developer", "SQL Developer");

	List<String> departmentlist = Arrays.asList("", "Development", "Sales", "Marketing", "HR", "Finance");

	List<String> categorylist = Arrays.asList("", "Sick Leave", "Casual Leave", "Marriage Leave", "Maternity Leave");

 
  
	@GetMapping("/{empid}")
	public String updateForm(@PathVariable Long empid, Model model) {
		baseUrl=ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		System.out.println(empid);
		Employee employee = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
	 
		model.addAttribute("employee", employee);
		
		model.addAttribute("designationlist", designationlist);
		model.addAttribute("departmentlist", departmentlist);
		System.out.println(employee);
		
		return "employee_edit";

	}

	@GetMapping("/admin/{empid}")
	public String adminUpdateForm(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		model.addAttribute("employee", employee);
		model.addAttribute("designationlist", designationlist);
		model.addAttribute("departmentlist", departmentlist);
		return "admin_edit";

	}


	@PostMapping("/update/{empid}")
	public String updateEmployee(Employee employee,@PathVariable long empid ) {
		
		Employee employeeexists= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		 
		
		
		employeeexists.setFirstname(employee.getFirstname());
		employeeexists.setLastname(employee.getLastname());
		employeeexists.setDob(employee.getDob());
		employeeexists.setDesignation(employee.getDesignation());
		employeeexists.setDepartment(employee.getDepartment());
		employeeexists.setUpdateddate(new Date());
		 
		
		String to = employeeexists.getEmail();
		String subject = "Profile Updated";

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employeeexists.getFirstname() + " " + employeeexists.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
	
		empRepo.save(employeeexists);
		try {
			notification.sendEmployeeUpdateMail(to, subject, templatemodel);
		} catch (Exception e) {
			logger.info("Error sending mail..................");

		}

 
		return  "redirect:/"+employeeexists.getEmpid()+"?success";

	}
	
	
	@PostMapping("/admin/update/{empid}")
	public String updateEmployeeByAdmin(Employee employee,@PathVariable Long empid ) {
		
		Employee employeeexists= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		 
		
		employeeexists.setFirstname(employee.getFirstname());
		employeeexists.setLastname(employee.getLastname());
		employeeexists.setDob(employee.getDob());
		employeeexists.setDesignation(employee.getDesignation());
		employeeexists.setDepartment(employee.getDepartment());
		employeeexists.setStatus(employee.getStatus());
		
		employeeexists.setUpdateddate(new Date());
		
		 
		
		String to = employeeexists.getEmail();
		String subject = "Profile Updated";

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employeeexists.getFirstname() + " " + employeeexists.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
	
		empRepo.save(employeeexists);


		return  "redirect:/admin/"+employeeexists.getEmpid()+"?success";

	}
	

	@GetMapping("/adminAccessRequest/{empid}")
	public String adminAccessRequest(@PathVariable Long empid) {
		System.out.println(empid);
		
		System.out.println("Alpha");
		
		Employee emp= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		
		
		System.out.println(emp);
		String to = company;
		String subject = "Admin access request";
	

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", emp.getFirstname() + " " + emp.getLastname());
		templatemodel.put("empid", emp.getEmpid());
		templatemodel.put("url", baseUrl);

		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
	 
		empRepo.save(emp);
		
		try {
			notification.sendAdminAccessRequestMail(to, subject, templatemodel);
		} catch (Exception e) {
			logger.info("Error sending mail..................");

		}
 
		return  "redirect:/?requestsuccess";
 

	}


	
	@GetMapping("/adminAccess/{empid}")
	public String adminAccessGranted(@PathVariable Long empid,Model model) {
	
		Employee emp= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		
		System.out.println(emp);
		
		Collection<EmployeeRole> role=new  ArrayList<EmployeeRole>();
		role.addAll(emp.getRole());
		role.add(new EmployeeRole("ADMIN"));
		
		emp.setRole(role);
		
		System.out.println(emp);
		
	
	
		//emp.setRole(Arrays.asList());
		
		
		
		emp.setUpdateddate(new Date());
		
		
		
		String to = emp.getEmail();
		String subject = "Admin access granted";
		
		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", emp.getFirstname() + " " + emp.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
	 
		empRepo.save(emp);
		
		try {
			notification.sendEmployeeUpdateMail(to, subject, templatemodel);
		} catch (Exception e) {
			logger.info("Error sending mail..................");

		}

		String message = "Thank you!";
		 
		model.addAttribute("message", message);
 

		return "response";

	}


	@GetMapping("/employeesList/{empid}")
	public String employeesList(@PathVariable Long empid, Model model) {
		Employee employee= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		List<Employee> employeesList = empRepo.findAllforAdmin(empid);

		System.out.println(employeesList);

		model.addAttribute("employeesList", employeesList);
		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());

		return "employees_list";

	}

	@GetMapping("/oof/{empid}")
	public String oof(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("employee", employee);
		model.addAttribute("oof", new OutofOffice());

		model.addAttribute("categorylist", categorylist);

		return "out_of_office_form";
	}

	@PostMapping("/oof/submit/{empid}")
	public String insertOOF(@PathVariable Long empid, OutofOffice oof ) throws ParseException {

		Employee employee = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		oof.setEmpid(empid);
		oof.setCreatedby(employee.getFirstname() + " " + employee.getLastname());
		oof.setStatus("Submitted");

		String startDate = oof.getStartdate();
		String endDate = oof.getEnddate();

		System.out.println(startDate + " " + endDate);

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		Date startdateformatted = dateformat.parse(startDate);
		Date enddateformatted = dateformat.parse(endDate);

		long duration = enddateformatted.getTime() - startdateformatted.getTime();

		int noofdays = (int) TimeUnit.MILLISECONDS.toDays(duration);

		System.out.println("No od days :" + noofdays);
		oof.setDays(noofdays + 1);

		String to = employee.getEmail();
		String subject = "Leave Request Submitted";
		String status = "Submitted";

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("category", oof.getCategory());
		templatemodel.put("days", oof.getDays());
		templatemodel.put("leaveperiod", oof.getStartdate() + " to " + oof.getEnddate());
		templatemodel.put("reason", oof.getReason());
		templatemodel.put("status", status);
		templatemodel.put("submittedby", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
		 
		System.out.println( templatemodel);

		oofRepo.save(oof);

		try {
			notification.sendOOFMail(to, subject, templatemodel);
		 
		} catch (Exception e) {
			logger.info("Error sending mail..................");
		}
 
		return  "redirect:/oof/"+employee.getEmpid()+"?success";
 

	}

	@GetMapping("/oof/requests/{empid}")
	public String oofRequestList(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		List<OutofOffice> oofList = oofRepo.findByEmpID(empid);

		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("oofList", oofList);

		return "oof_request_lists";
	}

	@GetMapping("/oof/employeesList/{empid}")
	public String requestsList(@PathVariable Long empid, Model model) {
		Employee employee= empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("empid", employee.getEmpid());
		List<OutofOffice> oofLists = oofRepo.findAllforAdmin(employee.getEmpid());

		model.addAttribute("oofLists", oofLists);

		return "oof_others_request_lists";

	}

	@GetMapping("/oof/update")
	public String updateOOF(@RequestParam(value="empid") Long empid,@RequestParam(value="oofid") Long id,@RequestParam(value="flag") String flag ,Model model) {
		OutofOffice oof = oofRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Out of office no found"));
		
		Employee employee= empRepo.findById(oof.getEmpid()).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		System.out.println();	
		System.out.println(employee);
		
		
			
		oof.setUpdateddate(new Date());
		String to = employee.getEmail();
	

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("category", oof.getCategory());
		templatemodel.put("days", oof.getDays());
		templatemodel.put("leaveperiod", oof.getStartdate() + " to " + oof.getEnddate());
		templatemodel.put("reason", oof.getReason());
		templatemodel.put("submittedby", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);
		 
		
		
		

		try {
					if(flag.equals("Yes"))
					{
						String status = "Approved";
						String subject = "Leave Request Approved";
						templatemodel.put("status", status);
						oof.setStatus(status);
						oofRepo.save(oof);
						System.out.println();	
						System.out.println(oof);
						System.out.println();
						System.out.println( templatemodel);
						notification.sendOOFMail(to, subject, templatemodel);
			
					}
					else if(flag.equals("No"))
					{
						String status = "Rejected";
						String subject = "Leave Request Rejected";
						templatemodel.put("status", status);
						oof.setStatus(status);
						oofRepo.save(oof);
						System.out.println();	
						System.out.println(oof);
						System.out.println();
						System.out.println( templatemodel);
						notification.sendOOFMail(to, subject, templatemodel);
						
					}
		} catch (Exception e) {
			logger.info("Error sending mail..................");
		}
  

		return  "redirect:/oof/employeesList/"+empid;

 

	}

}
