package com.alpha.employeelogin.dto;

import java.util.Date;

public class EmployeeDTO {

	private String firstname;

	private String lastname;

	private String email;

	private String password;

	private String dob;

	private String department;

	private String designation;

	private String status;

	public EmployeeDTO() {}

		
	public EmployeeDTO(String firstname, String lastname, String email, String password, String dob, String department,
			String designation, String status) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.department = department;
		this.designation = designation;
		this.status = status;
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

	@Override
	public String toString() {
		return "EmployeeDTO [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", dob=" + dob + ", department=" + department + ", designation=" + designation
				+ ", status=" + status + "]";
	}
	
	
	

	

	

}
