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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.alpha.employeelogin.dto.EmployeeDTO;
import com.alpha.employeelogin.dto.EmployeeOOFDTO;
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
	private EntityManagerFactory emf;

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
		baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		System.out.println(baseUrl);
		System.out.println();
		System.out.println(empid);

		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		model.addAttribute("employee", employee);

		model.addAttribute("designationlist", designationlist);
		model.addAttribute("departmentlist", departmentlist);
		System.out.println(employee);

		return "employee_edit";

	}

	@GetMapping("/admin/{empid}")
	public String adminUpdateForm(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		model.addAttribute("employee", employee);
		model.addAttribute("designationlist", designationlist);
		model.addAttribute("departmentlist", departmentlist);
		return "admin_edit";

	}

	@PostMapping("/update/{empid}")
	public String updateEmployee(Employee employee, @PathVariable long empid) {

		Employee employeeexists = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

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

		return "redirect:/" + employeeexists.getEmpid() + "?success";

	}

	@PostMapping("/admin/update/{empid}")
	public String updateEmployeeByAdmin(Employee employee, @PathVariable Long empid) {

		Employee employeeexists = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		employeeexists.setFirstname(employee.getFirstname());
		employeeexists.setLastname(employee.getLastname());
		employeeexists.setDob(employee.getDob());
		employeeexists.setDesignation(employee.getDesignation());
		employeeexists.setDepartment(employee.getDepartment());
		employeeexists.setStatus(employee.getStatus());

		employeeexists.setUpdateddate(new Date());

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employeeexists.getFirstname() + " " + employeeexists.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);

		empRepo.save(employeeexists);

		return "redirect:/admin/" + employeeexists.getEmpid() + "?success";

	}

	@GetMapping("/adminAccessRequest/{empid}")
	public String adminAccessRequest(@PathVariable Long empid) {

		baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		System.out.println(baseUrl);
		System.out.println();
		System.out.println(empid);

		System.out.println("Alpha");

		Employee emp = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

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

		return "redirect:/?requestsuccess";

	}

	@GetMapping("/adminAccess/{empid}")
	public String adminAccessGranted(@PathVariable Long empid, Model model) {

		Employee emp = empRepo.findById(empid).orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		System.out.println(emp);

		Collection<EmployeeRole> role = new ArrayList<EmployeeRole>();
		role.addAll(emp.getRole());
		role.add(new EmployeeRole("ADMIN"));

		emp.setRole(role);

		System.out.println(emp);

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
		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		List<Employee> employeesList = empRepo.findAllforAdmin(empid);

		System.out.println(employeesList);

		model.addAttribute("employeesList", employeesList);
		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());

		return "employees_list";

	}

	@GetMapping("/oof/{empid}")
	public String oof(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("employee", employee);
		model.addAttribute("oof", new OutofOffice());

		model.addAttribute("categorylist", categorylist);

		return "out_of_office_form";
	}

	@PostMapping("/oof/submit/{empid}")
	public String insertOOF(@PathVariable Long empid, OutofOffice oof) throws ParseException {

		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		oof.setEmpid(empid);
		oof.setCreatedby(employee.getFirstname() + " " + employee.getLastname());
		oof.setStatus("Submitted");

		Date startdate = oof.getStartdate();
		Date enddate = oof.getEnddate();

		long duration = enddate.getTime() - startdate.getTime();

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
		templatemodel.put("startdate", oof.getStartdate());
		templatemodel.put("enddate", oof.getEnddate());
		templatemodel.put("reason", oof.getReason());
		templatemodel.put("status", status);
		templatemodel.put("submittedby", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);

		System.out.println(templatemodel);

		oofRepo.save(oof);

		try {
			notification.sendOOFMail(to, subject, templatemodel);

		} catch (Exception e) {
			logger.info("Error sending mail..................");
		}

		return "redirect:/oof/" + employee.getEmpid() + "?success";

	}

	@GetMapping("/oof/requests/{empid}")
	public String oofRequestList(@PathVariable Long empid, Model model) {

		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));

		List<OutofOffice> oofList = oofRepo.findByEmpID(empid);

		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("oofList", oofList);

		return "oof_request_lists";
	}

	@GetMapping("/oof/employeesList/{empid}")
	public String requestsList(@PathVariable Long empid, Model model) {
		Employee employee = empRepo.findById(empid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));


//		
//		EntityManager em=emf.createEntityManager();
//		
//		em.getTransaction().begin();
//		 Query q=em.createQuery("SELECT new com.alpha.employeelogin.dto.EmployeeOOF( e.emp_id,concat(e.first_name+' '+ e.last_name),e.email ,oof.id ,oof.start_date ,oof.end_date ,oof.no_of_days ,oof.reason,oof.category,oof.status,oof.created_date,oof.created_by,oof.updated_date,oof.updated_by) FROM Employee e LEFT JOIN e.OutofOffice oof on e.emp_id=oof.emp_id  WHERE e.emp_id = :empid ", EmployeeOOF.class);
//				
//		
//		q.setParameter("empid", 1000);
//		
//		List<EmployeeOOF> oofLists=q.getResultList();
//		
//		//String str="SELECT  e.emp_id empid,e.first_name firstname,e.last_name lastname,e.email email,oof.id oofid,oof.start_date startdate,oof.end_date enddate ,oof.no_of_days days,oof.reason reason,oof.category category,oof.status status,oof.created_date createddate,oof.created_by createdby,oof.updated_date updateddate,oof.updated_by updatedby  FROM employees e left join out_of_office oof on e.emp_id=oof.emp_id   where e.emp_id = ?";
//		 
//		 
//		//SELECT  e.emp_id empid,e.first_name firstname,e.last_name lastname,e.email email,oof.id oofid,oof.start_date startdate,oof.end_date enddate ,oof.no_of_days days,oof.reason reason,oof.category category,oof.status status,oof.created_date createddate,oof.created_by createdby,oof.updated_date updateddate,oof.updated_by updatedby  FROM employees e left join out_of_office oof on e.emp_id=oof.emp_id   where e.emp_id = :empid
//      
//		em.getTransaction();
//		em.close();

		List<EmployeeOOFDTO> empOOFLists = new ArrayList<EmployeeOOFDTO>();
		List<Employee> employeeList = empRepo.findAllforAdmin(empid);

		for (Employee emp : employeeList) {
			List<OutofOffice> oofList = emp.getOutofOffices();
			for (OutofOffice oof : oofList) {
				empOOFLists.add(new EmployeeOOFDTO(emp.getEmpid(), emp.getFirstname() + " " + emp.getLastname(),
						emp.getEmail(), oof.getId(), oof.getStartdate(), oof.getEnddate(), oof.getDays(),
						oof.getReason(), oof.getCategory(), oof.getStatus(), oof.getCreateddate(), oof.getCreatedby(),
						oof.getUpdateddate(), oof.getUpdatedby()));

			}

		}

		System.out.println(empOOFLists);

		model.addAttribute("name", employee.getFirstname() + " " + employee.getLastname());
		model.addAttribute("empid", employee.getEmpid());

		model.addAttribute("oofLists", empOOFLists);

		return "oof_others_request_lists";

	}

	@GetMapping("/oof/update")
	public String updateOOF(@RequestParam(value = "empid") Long empid, @RequestParam(value = "oofid") Long id,
			@RequestParam(value = "flag") String flag, Model model) {
		OutofOffice oof = oofRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Out of office no found"));

		Employee employee = empRepo.findById(oof.getEmpid())
				.orElseThrow(() -> new ResourceNotFoundException("Employee no found"));
		System.out.println();
		System.out.println(employee);

		oof.setUpdateddate(new Date());
		String to = employee.getEmail();

		Map<String, Object> templatemodel = new HashMap<String, Object>();
		templatemodel.put("name", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("category", oof.getCategory());
		templatemodel.put("days", oof.getDays());
		templatemodel.put("startdate", oof.getStartdate());
		templatemodel.put("enddate", oof.getEnddate());
		templatemodel.put("reason", oof.getReason());
		templatemodel.put("submittedby", employee.getFirstname() + " " + employee.getLastname());
		templatemodel.put("signature", signature);
		templatemodel.put("location", location);

		try {
			if (flag.equals("Yes")) {
				String status = "Approved";
				String subject = "Leave Request Approved";
				templatemodel.put("status", status);
				oof.setStatus(status);
				oofRepo.save(oof);
				System.out.println();
				System.out.println(oof);
				System.out.println();
				System.out.println(templatemodel);
				notification.sendOOFMail(to, subject, templatemodel);

			} else if (flag.equals("No")) {
				String status = "Rejected";
				String subject = "Leave Request Rejected";
				templatemodel.put("status", status);
				oof.setStatus(status);
				oofRepo.save(oof);
				System.out.println();
				System.out.println(oof);
				System.out.println();
				System.out.println(templatemodel);
				notification.sendOOFMail(to, subject, templatemodel);

			}
		} catch (Exception e) {
			logger.info("Error sending mail..................");
		}

		return "redirect:/oof/employeesList/" + empid;

	}

}
