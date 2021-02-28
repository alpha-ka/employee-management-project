package com.alpha.employeelogin.dto;

 
 
 

public class OutofOfficeDTO {
	
 
	private long empid;
	
	 
	private String startdate;
	
 
	private String enddate;
  
	 
	private String reason;
	
 
	private String category;

	public OutofOfficeDTO()
	{
		
	}
	
	
	public OutofOfficeDTO(long empid, String startdate, String enddate, String reason, String category) {
		super();
		this.empid = empid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.reason = reason;
		this.category = category;
	}


	public long getEmpid() {
		return empid;
	}


	public void setEmpid(long empid) {
		this.empid = empid;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getEnddate() {
		return enddate;
	}


	public void setEnddate(String enddate) {
		this.enddate = enddate;
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


	@Override
	public String toString() {
		return "OutofOfficeDTO [empid=" + empid + ", startdate=" + startdate + ", enddate=" + enddate + ", reason="
				+ reason + ", category=" + category + "]";
	}
	
	  
	

}
