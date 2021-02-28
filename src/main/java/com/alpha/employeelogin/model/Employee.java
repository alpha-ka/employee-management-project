package com.alpha.employeelogin.model;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;





@Entity
@Table(name="employees")
public class Employee {

	
	@Id
	//@SequenceGenerator(name="emp_seq" , initialValue = 1000  )
	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "emp_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_id")
	private long empid;
	
	@Column(name="first_name",nullable = false,columnDefinition = "varchar(25)")
	private String firstname;
	
	@Column(name="last_name",nullable = false,columnDefinition = "varchar(25)")
	private String lastname;
	
	@Column(nullable = false , unique = true,columnDefinition = "varchar(100)")
	private String email;
	
	@Column(nullable = false,columnDefinition = "varchar(100)")
	private String password;
	
	@Column(name="date_of_birth", nullable = false,columnDefinition = "date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String dob;
	
	@Column(nullable = false,columnDefinition = "varchar(100)")
	private String department;
	
	@Column(nullable = false,columnDefinition = "varchar(100)")
	private String designation;
	
	@Column(nullable = false,insertable=false,columnDefinition = "varchar(20) default 'Active'")
	private String status;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="employees_roles",
			joinColumns = @JoinColumn(name="employee_id" ,referencedColumnName = "emp_id"),
			inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
			)
	private Collection<EmployeeRole> role;
	
	//@Column(name="created_date", nullable = false, insertable=false,updatable = false, columnDefinition="DATETIME DEFAULT GETDATE() ")
	@Column(name="created_date", nullable = false, insertable=false,updatable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP  ")
	private Date createddate;
	
	@Column(name="updated_date",  insertable=false, columnDefinition="DATETIME")	
	private Date updateddate;
	
	@OneToMany(targetEntity = OutofOffice.class,  cascade = CascadeType.ALL)
	@JoinColumn(name = "emp_id")
	private List<OutofOffice> outofOffices;
	

	public Collection<EmployeeRole> getRole() {
		return role;
	}
	public void setRole(Collection<EmployeeRole> role) {
		this.role = role;
	}
	public List<OutofOffice> getOutofOffices() {
		return outofOffices;
	}
	public void setOutofOffices(List<OutofOffice> outofOffices) {
		this.outofOffices = outofOffices;
	}
	
	public Employee() {}
	
	 
	public Employee(String firstname, String lastname, String email, String password, String dob, String department,
			String designation, String status, Collection<EmployeeRole> role) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.department = department;
		this.designation = designation;
		this.status = status;
		this.role = role;
	}
	public long getEmpid() {
		return empid;
	}
	public void setEmpid(long empid) {
		this.empid = empid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
  
	
	
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Date getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
 
	
	
	
	
	@Override
	public String toString() {
		return "Employee [empid=" + empid + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", dob=" + dob + ", department=" + department + ", designation="
				+ designation + ", status=" + status + ", role=" + role + ", createddate=" + createddate
				+ ", updateddate=" + updateddate + ", outofOffices=" + outofOffices + "]";
	}
	
}
