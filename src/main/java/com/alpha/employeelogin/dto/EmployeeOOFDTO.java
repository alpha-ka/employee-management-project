package com.alpha.employeelogin.dto;

import java.util.Date;



public class EmployeeOOFDTO {
	
 
	public long empid;
	
	
	public String name;	

	
	public String email;
	
	
	
	public long oofid;
	
	public Date startdate;
	
	public Date enddate;
	
	public int days;
	
	public String reason;
	
	public String category;
	
	public String status;
	
	public Date createddate;
	

	public String createdby;

	public Date updateddate;
	
	public String updatedby;
	
	public EmployeeOOFDTO() {}

	public EmployeeOOFDTO(long empid, String name, String email, long oofid, Date startdate, Date enddate, int days,
			String reason, String category, String status, Date createddate, String createdby, Date updateddate,
			String updatedby) {
		super();
		this.empid = empid;
		this.name = name;
		this.email = email;
		this.oofid = oofid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.days = days;
		this.reason = reason;
		this.category = category;
		this.status = status;
		this.createddate = createddate;
		this.createdby = createdby;
		this.updateddate = updateddate;
		this.updatedby = updatedby;
	}

	public long getEmpid() {
		return empid;
	}

	public void setEmpid(long empid) {
		this.empid = empid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getOofid() {
		return oofid;
	}

	public void setOofid(long oofid) {
		this.oofid = oofid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@Override
	public String toString() {
		return "EmployeeOOF [empid=" + empid + ", name=" + name + ", email=" + email + ", oofid=" + oofid
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", days=" + days + ", reason=" + reason
				+ ", category=" + category + ", status=" + status + ", createddate=" + createddate + ", createdby="
				+ createdby + ", updateddate=" + updateddate + ", updatedby=" + updatedby + "]";
	}
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
