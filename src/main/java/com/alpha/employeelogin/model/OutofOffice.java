package com.alpha.employeelogin.model;

 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="out_of_office")

public class OutofOffice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false)
	private long id;
	
	@Column(name="emp_id",nullable = false)
	private long empid;
	
	
	@Column(name="start_date",nullable = false,columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startdate;
	
	@Column(name="end_date",nullable = false,columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enddate;
	
	@Column(name="no_of_days",nullable = false)
	private int days;
	
	@Column(nullable = false,columnDefinition = "VARCHAR(1500)")
	private String reason;
	
	@Column(  nullable = false)
	private String category;
	
	@Column(  nullable = false)
	private String status;
	
	//@Column(name="created_date",nullable = false,insertable=false,updatable = false,columnDefinition = "datetime Default GETDATE()")
	@Column(name="created_date",nullable = false,insertable=false,updatable = false,columnDefinition = "timestamp Default NOW()")
	private Date createddate;
	
	@Column(name="created_by",nullable = false,updatable = false)
	private String createdby;
	
	//@Column(name="updated_date",  insertable=false, columnDefinition="DATETIME")	
	@Column(name="updated_date",  insertable=false, columnDefinition="timestamp")
	private Date updateddate;
	
	@Column(name="updated_by",insertable=false)
	private String updatedby;

	public OutofOffice() {}

	public OutofOffice(long empid, Date startdate, Date enddate, int days, String reason, String category,
			String status, String createdby) {
		super();
		this.empid = empid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.days = days;
		this.reason = reason;
		this.category = category;
		this.status = status;
		this.createdby = createdby;
	}
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmpid() {
		return empid;
	}

	public void setEmpid(long empid) {
		this.empid = empid;
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
		return "OutofOffice [id=" + id + ", empid=" + empid + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", days=" + days + ", reason=" + reason + ", category=" + category + ", status=" + status
				+ ", createddate=" + createddate + ", createdby=" + createdby + ", updateddate=" + updateddate
				+ ", updatedby=" + updatedby + "]";
	}

	 
 
	 
	
	

}
